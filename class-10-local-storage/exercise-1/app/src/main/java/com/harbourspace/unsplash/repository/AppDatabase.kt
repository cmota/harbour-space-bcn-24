package com.harbourspace.unsplash.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.harbourspace.unsplash.model.UnsplashItem
import java.util.concurrent.Executors

@Database(entities = [UnsplashItem::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun unsplashDao(): UnsplashDAO

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        val databaseWriteExecutor = Executors.newFixedThreadPool(2)

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val db = Room.databaseBuilder(context, AppDatabase::class.java, "db").build()
                INSTANCE = db
                db
            }
        }
    }
}