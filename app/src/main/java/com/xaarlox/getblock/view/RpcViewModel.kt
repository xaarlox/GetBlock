package com.xaarlox.getblock.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xaarlox.getblock.data.remote.dto.BlockResult
import com.xaarlox.getblock.data.remote.dto.EpochInfoResult
import com.xaarlox.getblock.data.remote.dto.RpcResponse
import com.xaarlox.getblock.data.remote.dto.SupplyResult
import com.xaarlox.getblock.repository.Repository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.intOrNull

class RpcViewModel : ViewModel() {
    private val repository = Repository()
    private var currentBlockCount = DEFAULT_BLOCK_COUNT
    private var autoUpdateJob: Job? = null
    private val _isAutoUpdating = MutableStateFlow(false)
    val isAutoUpdating: StateFlow<Boolean> = _isAutoUpdating

    private val _epochState = MutableStateFlow<RpcResponse<EpochInfoResult>?>(null)
    val epochState: StateFlow<RpcResponse<EpochInfoResult>?> = _epochState

    private val _supplyState = MutableStateFlow<RpcResponse<SupplyResult>?>(null)
    val supplyState: StateFlow<RpcResponse<SupplyResult>?> = _supplyState

    private val _blockState = MutableStateFlow<RpcResponse<BlockResult>?>(null)
    val blockState: StateFlow<RpcResponse<BlockResult>?> = _blockState

    private val _slotState = MutableStateFlow<RpcResponse<JsonPrimitive>?>(null)
    val slotState: StateFlow<RpcResponse<JsonPrimitive>?> = _slotState

    private val _blockListState = MutableStateFlow<RpcResponse<List<Int>>?>(null)
    val blockListState: StateFlow<RpcResponse<List<Int>>?> = _blockListState

    fun fetchEpochInfo() {
        viewModelScope.launch {
            try {
                val response = repository.getEpoch()
                _epochState.value = response
                Log.d("RpcViewModel", "EpochInfo: $response")
            } catch (exception: Exception) {
                Log.e("RpcViewModel", "fetchEpochInfo() Exception: ${exception.message}", exception)
            }
        }
    }

    fun fetchSupply() {
        viewModelScope.launch {
            try {
                val response = repository.getSupply()
                _supplyState.value = response
                Log.d("RpcViewModel", "Supply: $response")
            } catch (exception: Exception) {
                Log.e("RpcViewModel", "fetchSupply() Exception: ${exception.message}", exception)
            }
        }
    }

    fun fetchBlock(specificSlot: Int? = null) {
        viewModelScope.launch {
            try {
                if (specificSlot == null) {
                    val slotResponse = repository.getLastSlot()
                    Log.d("RpcViewModel", "Slot Response: $slotResponse")
                    val lastSlot = slotResponse.result?.intOrNull
                    Log.d("RpcViewModel", "Last Slot: $lastSlot")
                    val response = repository.getBlock(lastSlot)
                    _blockState.value = response
                    Log.d("RpcViewModel", "Block: $response")
                } else {
                    val response = repository.getBlock(specificSlot)
                    _blockState.value = response
                    Log.d("RpcViewModel", "Block: $response")
                }
            } catch (exception: Exception) {
                Log.e(
                    "RpcViewModel",
                    "fetchBlock() Exception: ${exception.message}",
                    exception
                )
            }
        }
    }

    fun fetchCurrentSlot() {
        viewModelScope.launch {
            try {
                val response = repository.getLastSlot()
                _slotState.value = response
                Log.d("RpcViewModel", "Current Slot: $response")
            } catch (exception: Exception) {
                Log.e(
                    "RpcViewModel",
                    "fetchCurrentSlot() Exception: ${exception.message}",
                    exception
                )
            }
        }
    }

    fun setCurrentBlockCount(count: Int) {
        currentBlockCount = count
        fetchLastBlocks()
    }

    fun fetchLastBlocks() {
        viewModelScope.launch {
            try {
                val slotResponse = repository.getLastSlot()
                val lastSlot = slotResponse.result?.intOrNull
                if (lastSlot != null) {
                    val blocksResponse =
                        repository.getBlocks(lastSlot - currentBlockCount, lastSlot)
                    _blockListState.value = blocksResponse
                    Log.d("RpcViewModel", "Last Blocks: $blocksResponse")
                }
            } catch (exception: Exception) {
                Log.e(
                    "RpcVewModel",
                    "fetchLastBlocks() Exception: ${exception.message}",
                    exception
                )
            }
        }
    }

    fun toggleAutoUpdate(intervalMillis: Long = DEFAULT_UPDATE_INTERVAL_MS) {
        if (autoUpdateJob != null) {
            autoUpdateJob?.cancel()
            autoUpdateJob = null
            _isAutoUpdating.value = false
            Log.d("RpcViewModel", "Auto update stopped")
        } else {
            autoUpdateJob = viewModelScope.launch {
                _isAutoUpdating.value = true
                while (isActive) {
                    fetchEpochInfo()
                    fetchSupply()
                    delay(intervalMillis)
                }
            }
            Log.d("RpcViewModel", "Auto update started")
        }
    }
    companion object {
        private const val DEFAULT_BLOCK_COUNT = 10
        private const val DEFAULT_UPDATE_INTERVAL_MS = 60000L
    }
}