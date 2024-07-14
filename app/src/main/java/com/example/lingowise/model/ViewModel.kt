package com.example.lingowise.model

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.lingowise.presentation.categories.Category
import com.example.lingowise.presentation.categorywords.Word
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.await

class ViewModel(context: Context): ViewModel() {
    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    private val _words = MutableStateFlow<List<Word>>(emptyList())
    val words: StateFlow<List<Word>> = _words

    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> = _token

    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_preferences", Context.MODE_PRIVATE)

    private val _watchedOnBoarding = MutableStateFlow<Boolean>(false)
    val watchedOnBoarding: StateFlow<Boolean> = _watchedOnBoarding

    private val _createdEmail = MutableStateFlow<String?>(null)
    val createdEmail: StateFlow<String?> = _createdEmail

    init {
        _token.value = sharedPreferences.getString("auth_token", null)
        _watchedOnBoarding.value = sharedPreferences.getBoolean("watchedOnBoarding", false)
    }

    @SuppressLint("CommitPrefEdits")
    fun setOnBoarding(){
        _watchedOnBoarding.value = true
        sharedPreferences.edit().putBoolean("watchedOnBoarding", true).apply()
    }

    fun singIn(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.signIn(SingInRequest(email, password)).await()
                Log.d("token1", "Token: ${response.token}")
                val token = response.token
                saveToken(token)
                _token.value = token
            } catch(e: Exception){
                Log.d("error", e.stackTraceToString())
            }
        }
    }

    fun signUp(email: String, password: String){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.signUp(SignUpRequest(email, password)).await()
                _createdEmail.value = response.email
            } catch (e: Exception){
                Log.d("error", e.stackTraceToString())
            }
        }
    }

    private fun saveToken(token: String) {
        sharedPreferences.edit().putString("auth_token", token).apply()
    }

    fun fetchWords(token: String, id: Int){
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getWordsByCategoryId(token, id).await()
                _words.value = response
            } catch (e: Exception){
                Log.d("Error!", e.stackTraceToString())
            }
        }
    }

    fun fetchCategories(token: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.instance.getAllCategories(token).await()
                _categories.value = response
            } catch (e: Exception) {
                Log.d("Error!", e.stackTraceToString())
            }
        }
    }
}

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(com.example.lingowise.model.ViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ViewModel(context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}