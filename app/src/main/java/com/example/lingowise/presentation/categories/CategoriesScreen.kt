package com.example.lingowise.presentation.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingowise.model.MyViewModelFactory
import com.example.lingowise.model.ViewModel

@Composable
fun CategoriesScreen(
    modifier: Modifier = Modifier,
    navigateTo: (id: Int) -> Unit
) {
    val viewModel: ViewModel = viewModel(factory = MyViewModelFactory(LocalContext.current))
    val categories by viewModel.categories.collectAsState()
    val token by viewModel.token.collectAsState()
    
    LaunchedEffect(Unit) {
        token?.let { viewModel.fetchCategories(it) }
    }

    val gradient = Brush.linearGradient(
        0.0f to Color(0xFF3100E2),
        500.0f to Color(0xFF8D008B),
        1000.0f to Color(0xFF190074),
        start = Offset.Zero,
        end = Offset.Infinite
    )

    LazyColumn(
        modifier  = Modifier
            .fillMaxWidth()
            .background(gradient),
        verticalArrangement = Arrangement.SpaceAround,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(categories){ category->
            CategoryItem(category = category, onClick = {navigateTo(it)})
        }
    }

}