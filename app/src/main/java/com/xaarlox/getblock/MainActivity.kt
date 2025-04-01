package com.xaarlox.getblock

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.xaarlox.getblock.ui.theme.GetBlockTheme
import com.xaarlox.getblock.ui.view.RpcViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: RpcViewModel = viewModel()
            LaunchedEffect(Unit) {
                //viewModel.fetchEpochInfo()
                //viewModel.fetchSupply()
                //viewModel.fetchBlock()
                //viewModel.fetchLastBlocks()
                viewModel.fetchCurrentSlot()
                //viewModel.toggleAutoUpdate()
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GetBlockTheme {
        Greeting("Android")
    }
}