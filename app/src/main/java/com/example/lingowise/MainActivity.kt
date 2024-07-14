package com.example.lingowise

import android.os.Bundle
import android.widget.Toolbar
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgs
import androidx.navigation.navArgument
import com.example.lingowise.model.MyViewModelFactory
import com.example.lingowise.model.ViewModel
import com.example.lingowise.presentation.auth.AuthScreen
import com.example.lingowise.presentation.categories.CategoriesScreen
import com.example.lingowise.presentation.categorywords.CategoryWordsScreen
import com.example.lingowise.presentation.onboarding.OnBoardingScreen
import com.example.lingowise.ui.theme.LingoWiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LingoWiseTheme {
                val navigationController = rememberNavController()
                val viewModel: ViewModel = viewModel(factory = MyViewModelFactory(this))
                val token by viewModel.token.collectAsState()
                val watchedOnBoarding by viewModel.watchedOnBoarding.collectAsState()

                val startDestination = when{
                    token!=null -> "categories_screen"
                    watchedOnBoarding -> "screen_auth"
                    else -> "screen_onboarding"
                }

                NavHost(navController = navigationController, startDestination = startDestination){
                    composable(
                        route = "screen_onboarding"
                    ){
                        OnBoardingScreen(navigateTo = {
                            navigationController.navigate("screen_auth")
                        })
                    }
                    composable(
                        route = "screen_auth"
                    ){
                        AuthScreen(
                            navigateTo = {
                                navigationController.navigate("categories_screen")
                            }
                        )
                    }

                    composable(
                        route = "categories_screen"
                    ){
                        CategoriesScreen(navigateTo = {
                            navigationController.navigate("category_words_screen/$it")
                        })
                    }

                    composable(
                        route = "category_words_screen/{category_id}",
                        arguments = listOf(navArgument("category_id"){type = NavType.StringType})
                    ){
                        val categoryId = it.arguments?.getString("category_id")
                        CategoryWordsScreen(id = categoryId!!.toInt())
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppToolBar(modifier: Modifier = Modifier, title: String) {
    TopAppBar(
        title = { Text(text = title)},
        navigationIcon = { 
            Icon(
            painter = painterResource(id = R.drawable.ic_launcher_background),
            contentDescription = null,
            tint = Color.White
        )
        }
    )
}

