package com.harbourspace.unsplash.ui.images

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harbourspace.unsplash.api.UnsplashApiProvider
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.cb.UnsplashResult

private const val TAG = "ImagesViewModel"

class ImagesViewModel : ViewModel(), UnsplashResult {

    private val _items = MutableLiveData<List<UnsplashItem>>()
    val items: LiveData<List<UnsplashItem>> = _items

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    private val provider by lazy {
        UnsplashApiProvider()
    }

    fun fetchImages() {
        provider.fetchImages(this)
    }

    fun forceFetchImages() {
        _loading.value = true
        fetchImages()
    }

    fun searchImages(keyword: String?) {
        keyword ?: return

        provider.searchImages(keyword, this)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
        _items.value = images
        _loading.value = false
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
        _loading.value = false
    }
}