package com.xaarlox.getblock.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
data class SupplyResult(
    val context: Context,
    val value: SupplyValue
)

@Serializable
@JsonIgnoreUnknownKeys
data class Context(
    val slot: Int
)

@Serializable
data class SupplyValue(
    val circulating: Long,
    val nonCirculating: Long,
    val nonCirculatingAccounts: List<String>,
    val total: Long
)