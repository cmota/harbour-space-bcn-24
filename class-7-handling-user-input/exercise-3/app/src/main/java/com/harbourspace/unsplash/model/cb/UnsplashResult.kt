package com.harbourspace.unsplash.model.cb

import com.harbourspace.unsplash.model.UnsplashItem

interface UnsplashResult {

    fun onDataFetchedSuccess(images: List<UnsplashItem>)

    fun onDataFetchedFailed()
}