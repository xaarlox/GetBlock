package com.xaarlox.getblock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
            LaunchedEffect(Unit) {
            }
        }
    }
}

@Composable
fun AppNavigation(navController: NavHostController, viewModel: RpcViewModel) {
    NavHost(navController = navController, startDestination = "main") {
        composable("main") {
            HomeScreen(viewModel, navController)
        }
    }
}