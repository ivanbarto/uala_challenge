package com.ivanbarto.data.cities.datasource.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.ivanbarto.data.cities.datasource.local.database.dao.CityDao
import com.ivanbarto.data.cities.datasource.local.database.entities.CityEntity

@Database(entities = [(CityEntity::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CityDataBase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}

class Database {
    companion object {
        fun provideDataBase(): CityDataBase =
            Room.databaseBuilder<CityDataBase>(Constants.DATABASE_NAME)
                .setDriver(BundledSQLiteDriver())
                .build()

        fun provideDao(dataBase: CityDataBase): CityDao = dataBase.getCityDao()
    }
}
