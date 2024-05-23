package com.ip.imagecachingapp.viewmodals.viewmodalfactory

import com.ip.imagecachingapp.repository.Repository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.ip.imagecachingapp.viewmodals.ImageViewModel

class ViewModalFactory(private val repo : Repository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ImageViewModel::class.java)){
            return ImageViewModel(repository = repo) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}