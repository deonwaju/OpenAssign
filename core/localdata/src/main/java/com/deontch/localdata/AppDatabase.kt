package com.deontch.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.deontch.localdata.dao.NewsDao
import com.deontch.localdata.typeconverter.Converters
import com.deontch.models.NewsFeedDomainModel

const val DB_NAME = "news_database"

@Database(entities = [NewsFeedDomainModel::class], version = 2, exportSchema = false)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao() : NewsDao
    companion object {
        const val DATABASE_NAME = DB_NAME
    }
}
