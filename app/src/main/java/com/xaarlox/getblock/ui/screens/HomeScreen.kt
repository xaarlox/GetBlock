package com.xaarlox.getblock.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.xaarlox.getblock.ui.components.BlockCard
import com.xaarlox.getblock.ui.components.BlockInfo
import com.xaarlox.getblock.ui.components.formatNumber
import com.xaarlox.getblock.ui.theme.Background
import com.xaarlox.getblock.ui.theme.Blue
import com.xaarlox.getblock.ui.theme.Pink
import com.xaarlox.getblock.ui.theme.Turquoise
import com.xaarlox.getblock.ui.view.RpcViewModel


@SuppressLint("DefaultLocale")
@Composable
fun HomeScreen(viewModel: RpcViewModel, navController: NavHostController) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchEpochInfo()
        viewModel.fetchSupply()
        viewModel.fetchLastBlocks()
    }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            Pink, Blue, Turquoise
                        )
                    )
                )
        ) {
            Column(
                modifier = Modifier
                    .statusBarsPadding()
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "GetBlock",
                    color = Color.White,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(Modifier.height(30.dp))
                Text(
                    text = "Explore Solana Blockchain",
                    color = Color.White,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Medium
                )
                Spacer(Modifier.height(15.dp))
            }
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(Background)
                .padding(horizontal = 20.dp)
                .padding(top = 10.dp, bottom = 10.dp)
        ) {
            item {
                BlockCard(
                    title = "SOL Supply",
                    mainValue = formatNumber(uiState.totalSupply, 2),
                    firstSubTitle = "Circulating Supply",
                    firstSubValue = "${formatNumber(uiState.circulatingSupply, 4)} SOL (${
                        String.format(
                            "%.1f",
                            uiState.percentCirculatingSupply
                        )
                    }%)",
                    secondSubTitle = "Non-circulating Supply",
                    secondSubValue = "${formatNumber(uiState.nonCirculatingSupply, 4)} SOL (${
                        String.format(
                            "%.1f",
                            uiState.percentNonCirculatingSupply
                        )
                    }%)"
                )
            }
            item {
                Spacer(Modifier.height(20.dp))
            }
            item {
                BlockCard(
                    title = "Current Epoch",
                    mainValue = uiState.epoch.toString(),
                    firstSubTitle = "Slot Range",
                    firstSubValue = "${uiState.slotRangeStart} to ${uiState.slotRangeEnd}",
                    secondSubTitle = "Time Remain",
                    secondSubValue = uiState.timeRemaining
                )
            }
            item {
                Spacer(Modifier.height(20.dp))
            }
            item {
                BlockInfo(
                    blockList = uiState.blocks,
                    onBlockClick = { block ->
                        viewModel.setCurrentBlock(block)
                        navController.navigate("block")
                    }
                )
            }
        }
    }
}