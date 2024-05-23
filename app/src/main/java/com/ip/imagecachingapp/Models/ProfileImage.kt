package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class ProfileImage(
    val large: String,
    val medium: String,
    val small: String
)