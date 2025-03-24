package com.xaarlox.getblock.repository

import android.util.Log
import com.xaarlox.getblock.data.remote.dto.BlockResult
import com.xaarlox.getblock.data.remote.dto.EpochInfoResult
import com.xaarlox.getblock.data.remote.dto.RpcRequest
import com.xaarlox.getblock.data.remote.dto.RpcResponse
import com.xaarlox.getblock.data.remote.dto.SupplyResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonPrimitive
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

class Repository {
    private val client = HttpClient {
        install(ContentNegotiation) {
            json(json = Json {
                ignoreUnknownKeys = true
                encodeDefaults = true
            })
        }
        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }
    }

    private suspend inline fun <reified T> postRpc(
        method: String,
        params: List<JsonElement> = emptyList()
    ): RpcResponse<T> {
        val request = RpcRequest(method = method, params = params)

        val responseText: String = client.post(HttpRoutes.BASE_URL) {
            contentType(ContentType.Application.Json)
            setBody(request)
        }.body()

        Log.d("Repository", "Raw response: $responseText")

        return Json.decodeFromString(responseText)
    }

    suspend fun getEpoch(): RpcResponse<EpochInfoResult> {
        return postRpc("getEpochInfo")
    }

    suspend fun getSupply(): RpcResponse<SupplyResult> {
        return postRpc("getSupply")
    }

    suspend fun getLastSlot(): RpcResponse<JsonPrimitive> {
        return postRpc("getSlot")
    }

    suspend fun getBlock(slot: Int?): RpcResponse<BlockResult> {
        val params = listOf(
            JsonPrimitive(slot),
            buildJsonObject { put("maxSupportedTransactionVersion", 0) })
        return postRpc("getBlock", params)
    }

    suspend fun getBlocks(startSlot: Int, endSlot: Int): RpcResponse<List<Int>> {
        val params = listOf(JsonPrimitive(startSlot), JsonPrimitive(endSlot))
        return postRpc("getBlocks", params)
    }
}