package com.example.lingowise.presentation.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun PageIndicator(
    modifier: Modifier = Modifier,
    pageSize: Int,
    indicatorSize: Int,
    selectedPage: Int,
    selectedColor: Color = Color(0xFFFF00EE),
    unselectedColor: Color = Color.White,
    onClick: (page: Int)-> Unit
) {
    Row(modifier = modifier, horizontalArrangement = Arrangement.SpaceBetween){
        repeat(pageSize){
            page ->
            Box(modifier = Modifier.size(indicatorSize.dp).clickable { onClick(page) }
                .clip(CircleShape)
                .background(color = if(page == selectedPage) selectedColor else unselectedColor))
        }
    }
}