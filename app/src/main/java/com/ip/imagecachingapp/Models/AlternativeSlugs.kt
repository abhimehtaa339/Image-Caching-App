package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class AlternativeSlugs(
    val de: String,
    val en: String,
    val es: String,
    val fr: String,
    val `it`: String,
    val ja: String,
    val ko: String,
    val pt: String
)