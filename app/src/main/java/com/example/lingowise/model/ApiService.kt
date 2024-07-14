package com.example.lingowise.model

import com.example.lingowise.presentation.categories.Category
import com.example.lingowise.presentation.categorywords.Word
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {
    @GET("api/words_category/get_many")
    fun getAllCategories(
        @Header("access-token") token: String
    ): Call<List<Category>>

    @GET("api/words/get_many_by_category/")
    fun getWordsByCategoryId(
        @Header("access-token") token: String,
        @Query("category_id") id: Int
    ):Call<List<Word>>

    @POST("api/users/auth/token/")
    @Headers("Accept: application/json", "Content-Type: application/json")
    fun signIn(
        @Body request: SingInRequest
    ): Call<SingInResponse>

    @POST("api/users/create")
    @Headers("Accept: application/json", "Content-Type: application/json")
    fun signUp(
        @Body request: SignUpRequest
    ): Call<SignUpResponse>
}
