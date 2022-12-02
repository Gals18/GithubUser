package com.galuhsaputri.core.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.galuhsaputri.core.data.local.db.dao.UserFavoriteDao
import com.galuhsaputri.core.data.local.db.entity.UserFavoriteEntity

@Database(
    entities = [UserFavoriteEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userFavDao(): UserFavoriteDao

}