package com.harbourspace.unsplash.ui.images

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.harbourspace.unsplash.api.UnsplashApiProvider
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.cb.UnsplashResult
import com.harbourspace.unsplash.repository.AppDatabase
import com.harbourspace.unsplash.repository.UnsplashRepository

private const val TAG = "ImagesViewModel"

class ImagesViewModel(application: Application) : AndroidViewModel(application), UnsplashResult {

    val database = AppDatabase.getDatabase(application)
    val repository = UnsplashRepository(database.unsplashDao())

    private val _items = MutableLiveData<List<UnsplashItem>>()
    val items: MediatorLiveData<List<UnsplashItem>> = MediatorLiveData<List<UnsplashItem>>().apply {
        addSource(_items) { this.value = _items.value }
        addSource(repository.items) { this.value = repository.items.value }
    }

    private val _collections = MutableLiveData<List<UnsplashItem>>()
    val collections: LiveData<List<UnsplashItem>> = _collections

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

    fun fetchCollections() {
        provider.fetchCollections(this)
    }

    override fun onDataFetchedSuccess(images: List<UnsplashItem>, fromCollection: Boolean) {
        Log.d(TAG, "onDataFetchedSuccess | Received ${images.size} images")
        if (fromCollection) {
            _collections.value = images
        } else {
            _items.value = images
            repository.insert(images)
        }

        _loading.value = false
    }

    override fun onDataFetchedFailed() {
        Log.e(TAG, "onDataFetchedFailed | Unable to retrieve images")
        _loading.value = false
    }
}