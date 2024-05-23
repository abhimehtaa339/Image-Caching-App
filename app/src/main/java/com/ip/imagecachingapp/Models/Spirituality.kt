package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Spirituality(
    val approved_on: String,
    val status: String
)