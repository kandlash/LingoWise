package com.example.lingowise.presentation.onboarding

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingowise.R
import com.example.lingowise.model.MyViewModelFactory
import com.example.lingowise.model.ViewModel
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingScreen(
    modifier: Modifier = Modifier,
    navigateTo: () -> Unit
) {
    val viewModel: ViewModel = viewModel(factory = MyViewModelFactory(LocalContext.current))

    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.onboardingbackground),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color(0xFF0A0219).copy(alpha = 0.92f)))
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        val pagerState = rememberPagerState(initialPage = 0) {
            pages.size
        }

        val coroutineScope = rememberCoroutineScope()

        val buttonState = remember{
            derivedStateOf {
                when(pagerState.currentPage){
                    0 -> listOf("Пропустить", "Далее")
                    1 -> listOf("Пропустить", "Далее")
                    2 -> listOf("", "Начать")
                    else -> listOf("", "")
                }
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        HorizontalPager(state = pagerState) { index->
            OnBoardingPage(page = pages[index])
        }
        
        Spacer(modifier = Modifier.height(25.dp))
        PageIndicator(
            modifier = Modifier.width(94.dp),
            pageSize = pages.size,
            indicatorSize = 18,
            selectedPage = pagerState.currentPage,
            onClick = {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(it)
                }
            }
        )
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .navigationBarsPadding(),
            horizontalArrangement = if(pagerState.currentPage != pages.size-1) Arrangement.SpaceBetween else Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){
            if(buttonState.value[0].isNotEmpty()){
                OnBoardingTextButton(
                    text = buttonState.value[0],
                    onClick = {
                        coroutineScope.launch { pagerState.animateScrollToPage(page = pages.size) }
                    }
                )
            }
            OnBoardingButton(
                text = buttonState.value[1],
                onClick = {
                    coroutineScope.launch {
                        if(pagerState.currentPage == pages.size-1){
                            viewModel.setOnBoarding()
                            navigateTo()
                        }else{
                            pagerState.animateScrollToPage(page = pagerState.currentPage + 1)
                        }
                    }
                }
            )
        }
    }
}