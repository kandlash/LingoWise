package com.example.lingowise.presentation.categorywords

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun WordItem(
    modifier: Modifier = Modifier,
    word: Word
) {
    val isOpened = remember {
        mutableStateOf(false)
    }

    Card(modifier = Modifier
        .padding(10.dp)
        .clickable { isOpened.value = !isOpened.value }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            if(!isOpened.value){
                Text(text = word.word)
            }
            else{
                Text(text = word.translate)
                Text(text = word.transcription)
            }
        }
    }

}