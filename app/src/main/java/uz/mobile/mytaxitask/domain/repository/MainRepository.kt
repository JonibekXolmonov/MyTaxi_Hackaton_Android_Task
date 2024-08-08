package uz.mobile.mytaxitask.domain.repository


import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.data.model.UserLocation

interface MainRepository {

    suspend fun insertUserCurrentLocation(userLocation: UserLocation)
    fun getLatestUserLocation():Flow<UserLocation?>

}