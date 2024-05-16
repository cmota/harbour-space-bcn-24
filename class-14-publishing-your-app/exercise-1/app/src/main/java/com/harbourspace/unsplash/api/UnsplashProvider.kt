package com.harbourspace.unsplash.api

import com.harbourspace.unsplash.model.SearchResult
import com.harbourspace.unsplash.model.UnsplashItem
import com.harbourspace.unsplash.model.cb.UnsplashResult
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

private const val BASE_URL = "https://api.unsplash.com/"

class UnsplashApiProvider {

    private val retrofit by lazy {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val client = OkHttpClient.Builder().addInterceptor(interceptor).build()

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create<UnsplashApi>()
    }

    fun fetchImages(cb: UnsplashResult) {
        retrofit.fetchPhotos().enqueue(object : Callback<List<UnsplashItem>> {
            override fun onResponse(
                call: Call<List<UnsplashItem>>,
                response: Response<List<UnsplashItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!, false)
                } else {
                    cb.onDataFetchedFailed()
                }

            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }

    fun searchImages(keyword: String, cb: UnsplashResult) {
        retrofit.searchPhotos(keyword).enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!.results, false)
                } else {
                    cb.onDataFetchedFailed()
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }

    fun fetchCollections(cb: UnsplashResult) {
        retrofit.fetchCollections().enqueue(object : Callback<List<UnsplashItem>> {
            override fun onResponse(
                call: Call<List<UnsplashItem>>,
                response: Response<List<UnsplashItem>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    cb.onDataFetchedSuccess(response.body()!!, true)
                } else {
                    cb.onDataFetchedFailed()
                }

            }

            override fun onFailure(call: Call<List<UnsplashItem>>, t: Throwable) {
                cb.onDataFetchedFailed()
            }
        })
    }
}
