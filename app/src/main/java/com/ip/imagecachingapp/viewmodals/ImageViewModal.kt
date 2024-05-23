package com.ip.imagecachingapp.viewmodals

import com.ip.imagecachingapp.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.ip.imagecachingapp.Models.ImageResponesItem
import kotlinx.coroutines.flow.Flow


class ImageViewModel(private val repository: Repository) : ViewModel() {


    fun getImages(): Flow<PagingData<ImageResponesItem>> {
        return repository.getData().cachedIn(viewModelScope)
    }



}

