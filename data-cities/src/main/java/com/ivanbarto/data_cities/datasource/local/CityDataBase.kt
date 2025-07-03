package com.ivanbarto.data_cities.datasource.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.ivanbarto.data_cities.datasource.local.dao.CityDao
import com.ivanbarto.data_cities.datasource.local.entities.CityEntity
import com.ivanbarto.data_cities.datasource.local.entities.Converters

@Database(entities = [(CityEntity::class)], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class CityDataBase : RoomDatabase() {
    abstract fun getCityDao(): CityDao
}

class Database {
    companion object {
        fun provideDataBase(context: Context): CityDataBase =
            Room.databaseBuilder<CityDataBase>(context, Constants.DATABASE_NAME)
                .build()

        fun provideDao(dataBase: CityDataBase): CityDao = dataBase.getCityDao()
    }
}