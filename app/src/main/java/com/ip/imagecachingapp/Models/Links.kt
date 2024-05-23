package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Links(
    val download: String,
    val download_location: String,
    val html: String,
    val self: String
)