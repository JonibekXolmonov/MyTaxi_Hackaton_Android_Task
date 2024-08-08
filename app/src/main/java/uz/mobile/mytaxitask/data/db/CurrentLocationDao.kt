package uz.mobile.mytaxitask.data.db

import androidx.room.*
import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.data.model.UserLocation

@Dao
interface UserLocationDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertUserCurrentLocation(userLocation: UserLocation)

    @Query("SELECT * FROM location ORDER BY timeStamp DESC LIMIT 1")
    fun getLatestUserLocation(): Flow<UserLocation?>

}