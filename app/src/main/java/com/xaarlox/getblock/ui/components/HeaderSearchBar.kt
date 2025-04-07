package com.xaarlox.getblock.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults.textFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.xaarlox.getblock.ui.theme.DarkGray
import com.xaarlox.getblock.ui.theme.Gray
import com.xaarlox.getblock.ui.theme.LightGray
import com.xaarlox.getblock.ui.theme.Pink
import com.xaarlox.getblock.ui.view.RpcViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HeaderSearchBar(
    modifier: Modifier = Modifier,
    viewModel: RpcViewModel,
    onSearchClick: (Long) -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }
    var showErrorDialog by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        color = Color.White,
        shadowElevation = 2.dp
    ) {
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = {
                Text(
                    "Search blocks",
                    color = LightGray,
                    fontSize = 16.sp
                )
            },
            textStyle = TextStyle(fontSize = 16.sp, color = Gray),
            singleLine = true,
            trailingIcon = {
                Box(
                    modifier = Modifier
                        .padding(end = 6.dp)
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Pink),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                            val number = searchQuery.toLongOrNull()
                            if (number != null && number in viewModel.uiState.value.slotRangeStart..viewModel.uiState.value.slotRangeEnd) {
                                onSearchClick(number)
                            } else {
                                showErrorDialog = true
                            }
                            focusManager.clearFocus()
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
            },
            colors = textFieldColors(
                containerColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent,
                cursorColor = LightGray
            ),
            modifier = Modifier.fillMaxWidth()
        )
    }

    if (showErrorDialog) {
        AlertDialog(
            onDismissRequest = { showErrorDialog = false },
            title = {
                Text(
                    "Smth went wrong :(",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = DarkGray
                )
            },
            text = { Text("Wrong input. Try again.", fontSize = 16.sp, color = Gray) },
            confirmButton = {
                TextButton(onClick = { showErrorDialog = false }) {
                    Text(
                        "Ok",
                        color = LightGray,
                        fontSize = 16.sp,
                    )
                }
            },
            dismissButton = null,
            containerColor = Color.White,
            shape = RoundedCornerShape(16.dp),
            properties = DialogProperties(
                dismissOnBackPress = true,
                dismissOnClickOutside = true
            )
        )
    }
}