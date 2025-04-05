package com.xaarlox.getblock.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaarlox.getblock.ui.theme.Blue
import com.xaarlox.getblock.ui.theme.DarkGray
import com.xaarlox.getblock.ui.theme.DividerColor
import com.xaarlox.getblock.ui.theme.Gray
import com.xaarlox.getblock.ui.view.Block

@Composable
fun BlockInfo(blockList: List<Block>, onBlockClick: (Block) -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(
                text = "Latest Blocks",
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = DarkGray, modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            )
            BlockHeader()
            Column {
                val currentTimestamp = System.currentTimeMillis() / 1000
                blockList.asReversed().forEachIndexed { index, block ->
                    BlockRow(
                        signature = block.signature,
                        time = block.time,
                        block = block.block.toString(),
                        onClick = { onBlockClick(block) },
                        showDivider = index != blockList.lastIndex,
                        currentTimestamp = currentTimestamp
                    )
                }
            }
        }
    }
}

@Composable
fun BlockHeader() {
    Row(modifier = Modifier.padding(vertical = 5.dp)) {
        Text(
            modifier = Modifier.weight(4f),
            text = "Signature",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
        Text(
            modifier = Modifier.weight(3f),
            text = "Time",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
        Text(
            modifier = Modifier.weight(3f),
            text = "Block",
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = DarkGray
        )
    }
    HorizontalDivider(color = DividerColor, thickness = 1.dp)
}

@Composable
fun BlockRow(
    signature: String,
    time: Long,
    block: String,
    onClick: () -> Unit,
    showDivider: Boolean,
    currentTimestamp: Long
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            modifier = Modifier.weight(4f),
            text = signature,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = Gray
        )
        Text(
            modifier = Modifier.weight(3f),
            text = calculateTime(time, currentTimestamp),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontSize = 14.sp,
            color = Gray
        )
        Text(
            modifier = Modifier
                .weight(3f)
                .clickable { onClick() },
            text = block,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = Blue,
            fontSize = 14.sp,
        )
    }
    if (showDivider) {
        HorizontalDivider(color = DividerColor, thickness = 1.dp)
    }
}

fun calculateTime(timestamp: Long, currentTimestamp: Long): String {
    val secondsAgo = currentTimestamp - timestamp
    return when {
        secondsAgo >= 86400 -> "${secondsAgo / 86400} days ago"
        secondsAgo >= 3600 -> "${secondsAgo / 3600} hours ago"
        secondsAgo >= 60 -> "${secondsAgo / 60} min ago"
        else -> "$secondsAgo secs ago"
    }
}