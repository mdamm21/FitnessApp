package com.example.fitnessapp.data

data class UserProfile(
    val gender: String = "",
    val age: Int = 0,
    val height: Float = 0f, // in cm
    val weight: Float = 0f, // in kg
    val goal: String = ""
)