package com.xaarlox.getblock.ui.view

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xaarlox.getblock.repository.Repository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.serialization.json.intOrNull
import kotlinx.serialization.json.longOrNull

class RpcViewModel : ViewModel() {
    private val repository = Repository()
    private val _searchedBlock = MutableStateFlow<Block?>(null)
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState

    fun fetchEpochInfo() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val epochResponse = repository.getEpoch().result
                    if (epochResponse != null) {
                        val slotRangeStart = epochResponse.epoch * epochResponse.slotsInEpoch
                        val slotRangeEnd =
                            epochResponse.absoluteSlot + (epochResponse.slotsInEpoch - epochResponse.slotIndex)
                        val timeRemaining =
                            countTimeRemain(epochResponse.absoluteSlot, slotRangeEnd)

                        _uiState.update { state ->
                            state.copy(
                                epoch = epochResponse.epoch,
                                slotRangeStart = slotRangeStart,
                                slotRangeEnd = slotRangeEnd,
                                timeRemaining = timeRemaining
                            )
                        }
                        Log.d("RpcViewModel", "EpochInfo: $epochResponse")
                    }
                } catch (exception: Exception) {
                    Log.e(
                        "RpcViewModel",
                        "fetchEpochInfo() Exception: ${exception.message}",
                        exception
                    )
                }
                delay(DEFAULT_UPDATE_INTERVAL_MS)
            }
        }
    }

    fun fetchSupply() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val supplyResponse = repository.getSupply().result
                    if (supplyResponse != null) {
                        val totalSupply = supplyResponse.value.total / SOL_VALUE
                        val circulatingSupply = supplyResponse.value.circulating / SOL_VALUE
                        val nonCirculatingSupply = supplyResponse.value.nonCirculating / SOL_VALUE

                        _uiState.value = _uiState.value.copy(
                            totalSupply = totalSupply,
                            circulatingSupply = circulatingSupply,
                            nonCirculatingSupply = nonCirculatingSupply,
                            percentCirculatingSupply = (circulatingSupply / totalSupply) * 100,
                            percentNonCirculatingSupply = (nonCirculatingSupply / totalSupply) * 100
                        )
                        Log.d("RpcViewModel", "Supply: $supplyResponse")
                    }
                } catch (exception: Exception) {
                    Log.e(
                        "RpcViewModel",
                        "fetchSupply() Exception: ${exception.message}",
                        exception
                    )
                }
                delay(DEFAULT_UPDATE_INTERVAL_MS)
            }
        }
    }

    fun fetchBlock(specificSlot: Long? = null) {
        viewModelScope.launch {
            try {
                val slot = specificSlot ?: repository.getLastSlot().result?.longOrNull
                if (slot != null) {
                    val blockResponse = repository.getBlock(slot).result
                    val epochResponse = repository.getEpoch().result
                    if (blockResponse != null && epochResponse != null) {
                        val block = Block(
                            block = slot.toLong(),
                            signature = blockResponse.blockhash,
                            time = blockResponse.blockTime.toLong(),
                            epoch = epochResponse.epoch,
                            rewardLamports = blockResponse.rewards.sumOf { it.lamports },
                            previousBlockHash = blockResponse.previousBlockhash
                        )
                        _uiState.update { currentState ->
                            currentState.copy(currentBlock = block)
                        }
                        if (specificSlot != null) {
                            _searchedBlock.value = block
                        }
                        Log.d("RpcViewModel", "Block: $blockResponse")
                    }
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

    fun fetchLastBlocks() {
        viewModelScope.launch {
            while (isActive) {
                try {
                    val lastSlot = repository.getLastSlot().result?.intOrNull
                    val epochResponse = repository.getEpoch().result
                    if (lastSlot != null && epochResponse != null) {
                        val startSlot = lastSlot - DEFAULT_BLOCK_COUNT
                        val blocksResponse = repository.getBlocks(startSlot, lastSlot)

                        if (blocksResponse.result != null) {
                            val blockHeights = blocksResponse.result as? List<Int>

                            if (blockHeights != null) {
                                val blocks = mutableListOf<Block>()
                                for (blockHeight in blockHeights) {
                                    val blockInfo = repository.getBlock(blockHeight.toLong())
                                    val blockResult = blockInfo.result
                                    if (blockResult != null) {
                                        val block = Block(
                                            block = blockHeight.toLong(),
                                            signature = blockResult.blockhash,
                                            time = blockResult.blockTime.toLong(),
                                            epoch = epochResponse.epoch,
                                            rewardLamports = blockResult.rewards.sumOf { it.lamports },
                                            previousBlockHash = blockResult.previousBlockhash
                                        )
                                        blocks.add(block)
                                    }
                                }
                                _uiState.value = _uiState.value.copy(blocks = blocks.toList())
                                Log.d("RpcViewModel", "Last Blocks: $blocks")
                            }
                        }
                    }
                } catch (exception: Exception) {
                    Log.e(
                        "RpcViewModel",
                        "fetchLastBlocks() Exception: ${exception.message}",
                        exception
                    )
                }
                delay(DEFAULT_UPDATE_INTERVAL_MS)
            }
        }
    }


    fun setCurrentBlock(block: Block?) {
        _uiState.update { currentState ->
            if (currentState.currentBlock != block) {
                currentState.copy(currentBlock = block)
            } else {
                currentState
            }
        }
    }

    private fun countTimeRemain(currentSlot: Long, endSlot: Long): String {
        val remainSlots = endSlot - currentSlot
        val remainSeconds = (remainSlots * 0.4).toLong()

        val days = (remainSeconds / 86400).toInt()
        val hours = ((remainSeconds % 86400) / 3600).toInt()
        val minutes = ((remainSeconds % 3600) / 60).toInt()
        val seconds = (remainSeconds % 60).toInt()

        return "${days}d ${hours}h ${minutes}m ${seconds}s"
    }

    companion object {
        private const val DEFAULT_BLOCK_COUNT = 10
        private const val DEFAULT_UPDATE_INTERVAL_MS = 60000L
        const val SOL_VALUE = 1_000_000_000.0
    }
}