package com.xaarlox.getblock.ui.view

data class UiState(
    //Supply
    val circulatingSupply: Double = 1.0,
    val nonCirculatingSupply: Double = 1.0,
    val totalSupply: Double = 1.0,

    val percentCirculatingSupply: Double = 1.1,
    val percentNonCirculatingSupply: Double = 1.1,

    //Epoch
    val epoch: Int = 1,
    val slotRangeStart: Int = 1,
    val slotRangeEnd: Long = 1,
    val timeRemaining: String = "",

    //Block
    val currentBlock: Block? = null,
    val blocks: List<Block> = emptyList(),
)