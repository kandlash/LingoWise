package com.example.lingowise.presentation.auth

import android.content.Context
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingowise.R
import com.example.lingowise.model.MyViewModelFactory
import com.example.lingowise.model.RetrofitClient
import com.example.lingowise.model.SingInRequest
import com.example.lingowise.model.ViewModel

@Composable
fun AuthScreen(modifier: Modifier = Modifier, navigateTo: ()-> Unit) {
    Box(modifier = Modifier.fillMaxSize()){
        Image(
            painter = painterResource(id = R.drawable.authscreen),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.matchParentSize()
        )
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .fillMaxHeight(0.5f)
                .fillMaxWidth(0.8f)
                .background(color = Color(0xFF0A0219), shape = RoundedCornerShape(60.dp))
                .padding(horizontal = 40.dp, vertical = 12.dp)
            ,
        ){
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                val emailText = remember {
                    mutableStateOf("")
                }
                val passwordText = remember{
                    mutableStateOf("")
                }

                val passwordConfirm = remember{
                    mutableStateOf("")
                }

                val isRegistration = remember {
                    mutableStateOf(false)
                }

                val viewModel: ViewModel = viewModel(factory = MyViewModelFactory(LocalContext.current))
                val createdEmail by viewModel.createdEmail.collectAsState()
                val token by viewModel.token.collectAsState()

                LaunchedEffect(createdEmail) {
                    if(createdEmail != null){
                        isRegistration.value = false
                        emailText.value = createdEmail.toString()
                    }
                }

                LaunchedEffect(token) {
                    if(token != null){
                        navigateTo()
                    }
                }

                Text(
                    text = stringResource(id = R.string.auth_text),
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    color = Color.White,
                    lineHeight = 32.sp
                )

                Spacer(modifier = Modifier.height(10.dp))
                AuthTextField(emailText, stringResource(id = R.string.emailText))
                AuthTextField(passwordText, stringResource(id = R.string.passwordText))
                if(!isRegistration.value){

                    AuthButton(stringResource(id = R.string.authButtonText)) {
                        viewModel.singIn(emailText.value, passwordText.value)
                    }


                    TextButton(onClick = { /*TODO*/ }) {
                        Text(
                            text = stringResource(id = R.string.retriveText),
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }


                    TextButton(onClick = { isRegistration.value = true}) {
                        Text(
                            text = stringResource(id = R.string.registrationButtonText),
                            color = Color(0xFF5C42F0),
                            fontSize = 16.sp
                        )
                    }
                }
                else{
                    AuthTextField(passwordConfirm, stringResource(id = R.string.confirmPasswordText))
                    AuthButton(text = stringResource(id = R.string.registrationButtonText)) {
                        if(passwordConfirm.value == passwordText.value){
                            viewModel.signUp(
                                emailText.value,
                                passwordText.value
                            )
                        }
                    }
                    TextButton(onClick = { isRegistration.value = false }) {
                        Text(
                            text = stringResource(id = R.string.autharizationTextButtonText),
                            color = Color(0xFF5C42F0),
                            fontSize = 16.sp
                        )
                    }
                }


            }
        }
    }
}

@Composable
private fun AuthButton(
    text: String,
    onClick: ()-> Unit
) {
    Button(
        onClick = { onClick() },
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonColors(
            containerColor = Color(0xFF5C42F0),
            contentColor = Color.White,
            disabledContentColor = Color(0xFF5C42F0).copy(alpha = 0.5f),
            disabledContainerColor = Color.White.copy(alpha = 0.5f)
        ),
        shape = RoundedCornerShape(10.dp)
    ) {
        Text(text = text, fontSize = 20.sp)
    }
}

@Composable
private fun AuthTextField(edText: MutableState<String>, labelText: String) {
    TextField(
        value = edText.value,
        onValueChange = { edText.value = it },
        label = { Text(text = labelText) },
        shape = RoundedCornerShape(10.dp),
        colors = TextFieldDefaults.colors(
            unfocusedLabelColor = Color.Black.copy(alpha = 0.63f),
            unfocusedContainerColor = Color.White,
            focusedLabelColor = Color.Black.copy(alpha = 0.63f),
            focusedContainerColor = Color.White
        )
    )
}