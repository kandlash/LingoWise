package com.example.lingowise.presentation.onboarding

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun OnBoardingButton(modifier: Modifier = Modifier, text: String, onClick:() -> Unit) {
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(6.dp)
    ) {
        Text(text = text)
    }
}

@Composable
fun OnBoardingTextButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    TextButton(onClick = onClick) {
        Text(text = text, color = Color.White)
    }
}