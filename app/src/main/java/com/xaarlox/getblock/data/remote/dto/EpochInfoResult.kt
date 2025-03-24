package com.xaarlox.getblock.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class EpochInfoResult(
    val absoluteSlot: Long,
    val blockHeight: Long,
    val epoch: Int,
    val slotIndex: Int,
    val slotsInEpoch: Int,
    val transactionCount: Long
)