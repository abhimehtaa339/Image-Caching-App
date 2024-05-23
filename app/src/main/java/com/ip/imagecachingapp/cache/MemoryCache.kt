package com.ip.imagecachingapp.cache

import android.graphics.Bitmap
import com.ip.imagecachingapp.Models.ImageResponesItem


import java.util.concurrent.ConcurrentHashMap

object MemoryCache {
    private val cache = ConcurrentHashMap<String, List<ImageResponesItem>>()

    fun getImages(position: Int): List<ImageResponesItem>? {
        val key = position.toString()
        return cache[key]
    }

    fun cacheImages(position: Int, images: List<ImageResponesItem>) {
        val key = position.toString()
        cache[key] = images
    }
}
