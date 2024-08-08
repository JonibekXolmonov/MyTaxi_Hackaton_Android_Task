package uz.mobile.mytaxitask.domain.datasource

import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.data.model.UserLocation


interface LocalDataSource {

    suspend fun insertCurrentLocation(userLocation: UserLocation)

    fun getLatestUserLocation(): Flow<UserLocation?>

}