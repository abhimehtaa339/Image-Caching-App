package com.ip.imagecachingapp.Models

import kotlinx.serialization.Serializable

@Serializable
data class ImageResponesItem(
    val alt_description: String,
    val alternative_slugs: AlternativeSlugs,
    val asset_type: String,
    val blur_hash: String,
    val breadcrumbs: List<Breadcrumb>,
    val color: String,
    val created_at: String,
    val current_user_collections: List<String>,
    val description: String,
    val height: Int,
    val id: String,
    val liked_by_user: Boolean,
    val likes: Int,
    val links: Links,
    val promoted_at: String,
    val slug: String,
    val sponsorship: String,
    val topic_submissions: TopicSubmissions,
    val updated_at: String,
    val urls: Urls,
    val user: User,
    val width: Int
)