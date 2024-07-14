package com.example.lingowise.presentation.categorywords

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingowise.model.MyViewModelFactory
import com.example.lingowise.model.ViewModel

@Composable
fun CategoryWordsScreen(
    modifier: Modifier = Modifier,
    id: Int
) {

    val viewModel: ViewModel = viewModel(factory = MyViewModelFactory(LocalContext.current))
    val words by viewModel.words.collectAsState()
    val token by viewModel.token.collectAsState()
    
    LaunchedEffect(Unit) {
        token?.let {
            viewModel.fetchWords(
                it,
                id
            )
        }
    }
    
    LazyColumn {
        items(words){word->
            WordItem(word = word)
        }
    }
    
}