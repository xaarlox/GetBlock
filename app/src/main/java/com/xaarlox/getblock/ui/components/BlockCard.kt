package com.xaarlox.getblock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaarlox.getblock.ui.theme.Background
import com.xaarlox.getblock.ui.theme.BackgroundInfo
import com.xaarlox.getblock.ui.theme.DarkGray
import com.xaarlox.getblock.ui.theme.DividerColor
import com.xaarlox.getblock.ui.theme.Gray
import com.xaarlox.getblock.ui.theme.LightGray
import java.text.NumberFormat
import java.util.Locale

@Composable
fun BlockCard(
    modifier: Modifier = Modifier,
    title: String = "",
    mainValue: String = "",
    firstSubTitle: String = "",
    firstSubValue: String = "",
    secondSubTitle: String = "",
    secondSubValue: String = "",
) {
    Card(
        modifier = modifier
            .background(BackgroundInfo, shape = RoundedCornerShape(12.dp))
            .fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Gray)
            Spacer(Modifier.height(8.dp))
            Text(
                text = mainValue,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
            Spacer(Modifier.height(16.dp))
            Column(
                modifier = Modifier
                    .background(Background, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = firstSubTitle, fontSize = 14.sp, color = LightGray)
                Text(
                    text = firstSubValue,
                    fontSize = 16.sp,
                    color = Gray
                )
                Divider(modifier = Modifier.padding(vertical = 8.dp), color = DividerColor)
                Text(text = secondSubTitle, fontSize = 14.sp, color = LightGray)
                Text(
                    text = secondSubValue,
                    fontSize = 16.sp,
                    color = Gray
                )
            }
        }
    }
}

fun formatNumber(value: Double, decimalPlaces: Int): String {
    val formatter = NumberFormat.getNumberInstance(Locale.US).apply {
        minimumFractionDigits = decimalPlaces
        maximumFractionDigits = decimalPlaces
    }
    return formatter.format(value)
}