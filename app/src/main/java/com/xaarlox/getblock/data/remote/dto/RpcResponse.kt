package com.xaarlox.getblock.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class RpcResponse<T>(
    val jsonrpc: String,
    val id: String? = null,
    val result: T? = null,
    val error: RpcError? = null
)

@Serializable
data class RpcError(
    val code: Int,
    val message: String
)