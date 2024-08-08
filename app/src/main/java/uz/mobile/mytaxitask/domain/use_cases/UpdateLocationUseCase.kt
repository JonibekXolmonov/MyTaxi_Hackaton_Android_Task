package uz.mobile.mytaxitask.domain.use_cases

import uz.mobile.mytaxitask.data.model.UserLocation
import uz.mobile.mytaxitask.domain.repository.MainRepository

class InsertUserLocationUseCase(
    private val mainRepository: MainRepository
) {

    suspend operator fun invoke(userLocation: UserLocation) {
        mainRepository.insertUserCurrentLocation(userLocation)
    }
}