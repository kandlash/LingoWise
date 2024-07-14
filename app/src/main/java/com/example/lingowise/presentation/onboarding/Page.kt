package com.example.lingowise.presentation.onboarding

import androidx.annotation.DrawableRes
import com.example.lingowise.R

data class Page(
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        description = "Рады предоставить вам наш продукт - LingoWise!\n" +
                "Данный сервис создан для помощи в освоении Английского языка посредством просмотра карточек!",
        image = R.drawable.onboarding
    ),
    Page(
        description = "С помощью LingoWise вы обязательно освоите Английский язык.\n" +
                "Мы сделаем всё для этого!",
        image = R.drawable.onboarding1
    ),
    Page(
        description = "Теперь, давайте начнём, вместе с  LingoWise! ",
        image = R.drawable.onboarding3
    )
)