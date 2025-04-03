package com.xaarlox.getblock.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaarlox.getblock.ui.components.calculateTime
import com.xaarlox.getblock.ui.theme.Background
import com.xaarlox.getblock.ui.theme.DarkGray
import com.xaarlox.getblock.ui.theme.Gray
import com.xaarlox.getblock.ui.theme.LightGray
import com.xaarlox.getblock.ui.view.RpcViewModel
import com.xaarlox.getblock.ui.view.RpcViewModel.Companion.SOL_VALUE
import com.xaarlox.getblock.ui.view.UiState

@Composable
fun BlockScreen(viewModel: RpcViewModel, blockNumber: Long?, onClose: () -> Unit) {
    val block = viewModel.uiState.collectAsState().value
    val solToUSD = 114.3

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Background)
    ) {
        IconButton(
            onClick = onClose, modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(15.dp)
        ) {
            Icon(imageVector = Icons.Default.Close, contentDescription = "Close")
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 24.dp, end = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Block Details",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .align(Alignment.Start)
                )
                Text(
                    text = "${block.currentBlock?.block}",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Medium,
                    color = DarkGray,
                    modifier = Modifier
                        .padding(bottom = 16.dp)
                        .align(Alignment.Start)
                )
            }
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(4.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                BlockGrid(block, solToUSD)
            }
        }
    }
}

@Composable
fun BlockGrid(block: UiState, solPrice: Double) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
    ) {
        val currentTimestamp = System.currentTimeMillis() / 1000
        val items = listOf(
            "Block" to (block.currentBlock?.block?.toString() ?: "N/A"),
            "Timestamp" to (block.currentBlock?.time?.let { calculateTime(it, currentTimestamp) }
                ?: "N/A"),
            "Block Hash" to (block.currentBlock?.signature ?: "N/A"),
            "Epoch" to (block.currentBlock?.epoch?.toString() ?: "N/A"),
            "Reward" to (block.currentBlock?.rewardLamports?.let { calculatePrice(it, solPrice) }
                ?: "N/A"),
            "Previous Block Hash" to (block.currentBlock?.previousBlockHash ?: "N/A")
        )
        items.forEach { (title, value) ->
            Column(modifier = Modifier.padding(vertical = 4.dp)) {
                Text(
                    text = title,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = LightGray
                )
                Text(text = value, fontSize = 14.sp, color = Gray)
            }
        }
    }
}

fun calculatePrice(rewardLamports: Int, solToUSD: Double): String {
    val solReward = rewardLamports / SOL_VALUE
    val usdReward = solReward * solToUSD
    return "%.6f SOL (%.2f USD)".format(solReward, usdReward)
}