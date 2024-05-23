package com.ip.imagecachingapp.views.adapters

import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ip.imagecachingapp.Models.ImageResponesItem
import com.ip.imagecachingapp.databinding.ImagesBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import android.widget.ImageView
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


class ImageAdapter() : PagingDataAdapter<ImageResponesItem, ImageAdapter.ImageViewHolder>(
    IMAGE_COMPARATOR
) {

    class ImageViewHolder(private val binding: ImagesBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(image: ImageResponesItem , context: Context) {
            ImageLoader(binding.imageview).loadImageFromUrl(image.urls.small)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val binding = ImagesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val item = getItem(position)

            if (item != null) {
                holder.bind(item , holder.itemView.context)
            }


    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<ImageResponesItem>() {
            override fun areItemsTheSame(
                oldItem: ImageResponesItem,
                newItem: ImageResponesItem
            ): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: ImageResponesItem,
                newItem: ImageResponesItem
            ): Boolean =
                oldItem == newItem
        }
    }





        class ImageLoader(private val imageView: ImageView) {

            @OptIn(DelicateCoroutinesApi::class)
            fun loadImageFromUrl(url: String) {
                GlobalScope.launch(Dispatchers.IO) {
                    try {
                        val imageUrl = URL(url)
                        val connection = imageUrl.openConnection() as HttpURLConnection
                        connection.connect()
                        val inputStream: InputStream = connection.inputStream
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        connection.disconnect()

                        launch(Dispatchers.Main) {
                            imageView.setImageBitmap(bitmap)
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

