package com.xaarlox.getblock.ui.components

import android.icu.text.CaseMap.Title
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.xaarlox.getblock.ui.theme.Background
import com.xaarlox.getblock.ui.theme.BackgroundInfo

@Composable
fun BlockCard(
    modifier: Modifier = Modifier,
    title: String = "",
    mainValue: String = "",
    firstSubTitle: String = "",
    firstSubValue: String = "",
    secondSubTitle: String = "",
    secondSubValue: String = "",
    onClick: () -> Unit = {}
) {
    Card(
        onClick = onClick,
        modifier = Modifier.background(Background),
        shape = RoundedCornerShape(12.dp)
    ) {
        Column(modifier = Modifier.padding(10.dp)) {
            Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(10.dp))
            Text(text = mainValue, fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Spacer(Modifier.height(10.dp))
            Column(
                modifier = Modifier
                    .background(BackgroundInfo, shape = RoundedCornerShape(12.dp))
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Text(text = firstSubTitle)
                Text(text = firstSubValue)
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
                Text(text = secondSubTitle)
                Text(text = secondSubValue)
            }
        }
    }
}