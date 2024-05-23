package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class Breadcrumb(
    val index: Int,
    val slug: String,
    val title: String,
    val type: String
)