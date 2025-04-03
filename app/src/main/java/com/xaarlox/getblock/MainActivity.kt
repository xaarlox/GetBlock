package com.xaarlox.getblock

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.xaarlox.getblock.ui.screens.BlockScreen
import com.xaarlox.getblock.ui.theme.GetBlockTheme
import com.xaarlox.getblock.ui.view.RpcViewModel
import com.xaarlox.getblock.ui.screens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val rpcViewModel = ViewModelProvider(this)[RpcViewModel::class.java]

        setContent {
            GetBlockTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    AppNavigation(navController, rpcViewModel)
                }
            }
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun AppNavigation(navController: NavHostController, viewModel: RpcViewModel) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            HomeScreen(
                viewModel,
                navController
            )
        }
        composable("block/{blockNumber}") { backStackEntry ->
            val blockNumber = backStackEntry.arguments?.getString("blockNumber")?.toLongOrNull()

            if (blockNumber == null) {
                navController.popBackStack()
                return@composable
            }

            val uiState = viewModel.uiState.collectAsState().value
            val selectedBlock = uiState.blocks.find { it.block == blockNumber }

            LaunchedEffect(blockNumber) {
                if (selectedBlock == null) {
                    viewModel.fetchBlock(blockNumber)
                }
            }
            BlockScreen(viewModel, onClose = {
                if (!navController.popBackStack()) {
                    navController.navigate("main")
                }
            })
        }
    }
}