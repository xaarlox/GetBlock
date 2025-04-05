package com.xaarlox.getblock.data

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@Serializable
@JsonIgnoreUnknownKeys
data class BlockResult(
    val blockHeight: Int,
    val blockTime: Int,
    val blockhash: String,
    val parentSlot: Int,
    val previousBlockhash: String,
    val rewards: List<Reward>,
)

@Serializable
data class Reward(
    val commission: JsonElement?,
    val lamports: Int,
    val postBalance: Long,
    val pubkey: String,
    val rewardType: String
)