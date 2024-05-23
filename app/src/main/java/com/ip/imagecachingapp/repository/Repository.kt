package com.ip.imagecachingapp.repository

import ImagePagingSource
import android.content.Context
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.ip.imagecachingapp.Retrofit.ApiService

class Repository(private val apiService: ApiService, private val context: Context) {


    fun getData() = Pager(
        config = PagingConfig(
            pageSize = 10,
            maxSize = 100
        ),
        pagingSourceFactory = { ImagePagingSource(apiService, context = context) }
    ).flow


}
