package com.xaarlox.getblock.data.remote.dto

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class RpcRequest(
    val jsonrpc: String = "2.0",
    val id: String = "getblock.io",
    val method: String,
    val params: List<JsonElement> = emptyList()
)