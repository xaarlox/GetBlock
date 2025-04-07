package com.xaarlox.getblock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaarlox.getblock.ui.theme.Background
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
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        modifier = modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = title, fontSize = 14.sp, fontWeight = FontWeight.Medium, color = Gray)
            Spacer(Modifier.height(8.dp))
            Text(
                text = mainValue,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = DarkGray
            )
            Spacer(Modifier.height(12.dp))
            Column(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Background)
                    .fillMaxWidth()
                    .padding(12.dp)
            ) {
                Text(text = firstSubTitle, fontSize = 13.sp, color = LightGray)
                Text(
                    text = firstSubValue,
                    fontSize = 16.sp,
                    color = Gray
                )
                HorizontalDivider(
                    modifier = Modifier.padding(vertical = 8.dp),
                    color = DividerColor
                )
                Text(text = secondSubTitle, fontSize = 13.sp, color = LightGray)
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