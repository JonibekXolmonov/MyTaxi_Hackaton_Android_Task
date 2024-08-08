package uz.mobile.mytaxitask.data.repository

import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.domain.datasource.LocalDataSource
import uz.mobile.mytaxitask.domain.repository.MainRepository
import uz.mobile.mytaxitask.data.model.UserLocation

class MainRepositoryImpl(
    private val localDataSource: LocalDataSource
): MainRepository {

    override suspend fun insertUserCurrentLocation(userLocation: UserLocation) {
        localDataSource.insertCurrentLocation(userLocation)
    }

    override  fun getLatestUserLocation(): Flow<UserLocation?> {
        return localDataSource.getLatestUserLocation()
    }
}
