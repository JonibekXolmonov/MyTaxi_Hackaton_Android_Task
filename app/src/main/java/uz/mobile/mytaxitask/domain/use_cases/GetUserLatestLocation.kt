package uz.mobile.mytaxitask.domain.use_cases

import kotlinx.coroutines.flow.Flow
import uz.mobile.mytaxitask.data.model.UserLocation
import uz.mobile.mytaxitask.domain.repository.MainRepository

class GetUserLatestLocation(
    private val mainRepository: MainRepository
) {
    operator fun invoke(): Flow<UserLocation?> {
        return mainRepository.getLatestUserLocation()
    }
}