package com.xaarlox.getblock.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.xaarlox.getblock.ui.components.BlockCard
import com.xaarlox.getblock.ui.theme.BackgroundInfo
import com.xaarlox.getblock.ui.view.RpcViewModel

@Composable
fun HomeScreen(viewModel: RpcViewModel, navController: NavHostController) {
    val uiState = viewModel.uiState.collectAsState().value

    LaunchedEffect(Unit) {
        viewModel.fetchEpochInfo()
        viewModel.fetchSupply()
        viewModel.fetchLastBlocks()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundInfo)
            .padding(horizontal = 20.dp)
            .padding(top = 10.dp)
    ) {
        item {
            BlockCard(
                title = "SOL Supply",
                mainValue = uiState.totalSupply.toString(),
                firstSubTitle = "Circulating Supply",
                firstSubValue = "${uiState.circulatingSupply} SOL (${String.format("%.1f", uiState.percentCirculatingSupply)}%)",
                secondSubTitle = "Non-circulating Supply",
                secondSubValue = "${uiState.nonCirculatingSupply} SOL (${String.format("%.1f", uiState.percentNonCirculatingSupply)}%)",
            )
        }
        item {
            Spacer(Modifier.height(20.dp))
        }
        item {
            BlockCard(
                title = "Epoch",
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
        /*item {
            BlockInfo(
                blockList = uiState.blocks,
                onBlockClick = { block ->
                    viewModel.fetchBlock(block.block.toInt()) // Отримання даних про конкретний блок
                    navController.navigate("block")
                }
            )
        }
        item {
            Text(text = uiState.toString())
        }*/
    }
}