package com.mobiquity.weatherapp.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobiquity.utils.Converter
import com.mobiquity.weatherapp.dao.CityDao
import com.mobiquity.weatherapp.dao.WeatherDao
import com.mobiquity.weatherapp.data.City
import com.mobiquity.weatherapp.data.CurrentWeatherEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        City::class,
        CurrentWeatherEntity::class],
    version = 2
)
@TypeConverters(Converter::class)
abstract class CityRoomDatabase : RoomDatabase() {

    abstract fun cityDao(): CityDao
    abstract fun weatherDao(): WeatherDao

    companion object {
        @Volatile
        private var INSTANCE: CityRoomDatabase? = null

        fun getDatabase(
            context: Context,
            scope: CoroutineScope
        ): CityRoomDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CityRoomDatabase::class.java,
                    "WeatherApp-DB"
                )
                    // Wipes and rebuilds instead of migrating if no Migration object.
                    // Migration is not part of this codelab.
                    .fallbackToDestructiveMigration()
                    .addCallback(CityDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }

        private class CityDatabaseCallback(
            private val scope: CoroutineScope
        ) : RoomDatabase.Callback() {
            /**
             * Override the onCreate method to populate the database.
             */
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                // If you want to keep the data through app restarts,
                // comment out the following line.
                INSTANCE?.let { database ->
                    scope.launch(Dispatchers.IO) {
                        populateDatabase(database.cityDao())
                    }
                }
            }
        }

        /**
         * Populate the database in a new coroutine.
         * If you want to start with more words, just add them.
         */
        suspend fun populateDatabase(cityDao: CityDao) {
            // Start the app with a clean database every time.
            // Not needed if you only populate on creation.

        }
    }
}