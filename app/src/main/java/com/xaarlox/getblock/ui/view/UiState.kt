package com.xaarlox.getblock.ui.view

data class UiState(
    //Supply
    val circulatingSupply: Long = 1,
    val nonCirculatingSupply: Long = 1,
    val totalSupply: Long = 1,

    val percentCirculatingSupply: Double = 1.1,
    val percentNonCirculatingSupply: Double = 1.1,

    //Epoch
    val epoch: Int = 1,
    val slotRangeStart: Int = 1,
    val slotRangeEnd: Long = 1,
    val timeRemaining: String = "",

    //Block
    val currentBlock: Block = Block(
        block = 1,
        signature = "",
        time = 1,
        epoch = 1,
        rewardLamports = 1,
        previousBlockHash = ""
    ),
    val blocks: List<Block> = emptyList(),
)