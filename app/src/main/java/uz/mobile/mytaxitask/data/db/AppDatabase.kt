package uz.mobile.mytaxitask.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import uz.mobile.mytaxitask.data.model.UserLocation

@Database(entities = [UserLocation::class], version = 2, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun userLocationDao(): UserLocationDao
}