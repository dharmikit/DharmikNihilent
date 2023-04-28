package com.app.dharmiknihilent.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.app.dharmiknihilent.database.converters.RoomConverters
import com.app.dharmiknihilent.models.ProductList

@Database(entities = [ProductList::class], version = 1)
@TypeConverters(RoomConverters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

}