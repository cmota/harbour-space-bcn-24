package com.harbourspace.unsplash.model

data class SearchResult(
    val results: List<UnsplashItem>,
    val total: Int,
    val total_pages: Int
)
