package com.xaarlox.getblock.ui.view

data class Block(
    val block: Long,
    val signature: String,
    val time: Long,
    val epoch: Int,
    val rewardLamports: Int,
    val previousBlockHash: String
)