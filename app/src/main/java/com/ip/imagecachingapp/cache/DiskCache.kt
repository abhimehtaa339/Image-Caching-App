package com.ip.imagecachingapp.cache

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.ip.imagecachingapp.Models.ImageResponesItem
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.ObjectInputStream
import java.io.ObjectOutputStream

object DiskCache {


    fun saveImages(context: Context, position: Int, images: List<ImageResponesItem>) {
        try {
            val file = File(context.cacheDir, "image_cache_$position")
            ObjectOutputStream(FileOutputStream(file)).use { oos ->
                oos.writeObject(images)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getImages(context: Context, position: Int): List<ImageResponesItem>? {
        val file = File(context.cacheDir, "image_cache_$position")
        if (file.exists()) {
            return try {
                ObjectInputStream(FileInputStream(file)).use { ois ->
                    @Suppress("UNCHECKED_CAST")
                    ois.readObject() as? List<ImageResponesItem>
                }
            } catch (e: Exception) {
                e.printStackTrace()
                null
            }
        }
        return null
    }
}


