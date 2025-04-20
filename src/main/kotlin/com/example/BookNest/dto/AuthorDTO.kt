package com.example.BookNest.dto

import java.time.LocalDate

data class AuthorDTO(
    val id: Int,
    val name: String,
    val birthDate: LocalDate
)