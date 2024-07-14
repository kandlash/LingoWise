package com.example.lingowise.presentation.categorywords

data class Word(
    val id: Int,
    val word: String,
    val transcription: String,
    val translate: String,
    val categoryId: Int
)
