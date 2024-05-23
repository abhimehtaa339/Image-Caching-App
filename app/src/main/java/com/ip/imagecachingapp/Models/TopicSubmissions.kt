package com.ip.imagecachingapp.Models

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class TopicSubmissions(
    @SerializedName("architecture-interior")
    val architecture_interior: ArchitectureInterior,
    val experimental: Experimental,
    val nature: Nature,
    val spirituality: Spirituality,
    @SerializedName("street-photography")
    val street_photography: StreetPhotography,
    val travel: Travel,
    val wallpapers: Wallpapers
)