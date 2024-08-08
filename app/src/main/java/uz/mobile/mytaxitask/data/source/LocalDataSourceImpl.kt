package uz.mobile.mytaxitask.data.source

import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.domain.datasource.LocalDataSource
import uz.mobile.mytaxitask.data.db.UserLocationDao
import uz.mobile.mytaxitask.data.model.UserLocation

class LocalDataSourceImpl(
    private val userLocationDao: UserLocationDao
) : LocalDataSource {

    override suspend fun insertCurrentLocation(userLocation: UserLocation) {
        userLocationDao.insertUserCurrentLocation(userLocation = userLocation)
    }

    override fun getLatestUserLocation(): Flow<UserLocation?> {
        return userLocationDao.getLatestUserLocation()
    }
}