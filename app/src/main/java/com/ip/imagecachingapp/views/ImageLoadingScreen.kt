package com.ip.imagecachingapp.views

import com.ip.imagecachingapp.repository.Repository
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import com.ip.imagecachingapp.views.adapters.ImageAdapter
import com.ip.imagecachingapp.Retrofit.RetrofitClient
import com.ip.imagecachingapp.viewmodals.viewmodalfactory.ViewModalFactory
import com.ip.imagecachingapp.databinding.ActivityImageLoadingScreenBinding
import com.ip.imagecachingapp.viewmodals.ImageViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ImageLoadingScreen : AppCompatActivity() {
    private lateinit var binding: ActivityImageLoadingScreenBinding
    private val adapter = ImageAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityImageLoadingScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val repository = Repository(RetrofitClient.api , this)
        val viewModelFactory = ViewModalFactory(repository)
        val viewModel = ViewModelProvider(this, viewModelFactory)[(ImageViewModel::class.java)]

        binding.recyclerview.layoutManager = GridLayoutManager(this, 3)
        binding.recyclerview.adapter = adapter

        lifecycleScope.launch {
            viewModel.getImages().collectLatest {
                adapter.submitData(it)
            }
        }

        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                // Handle errors
                val errorState = loadState.refresh as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                errorState?.let {
                    Toast.makeText(this, it.error.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}


