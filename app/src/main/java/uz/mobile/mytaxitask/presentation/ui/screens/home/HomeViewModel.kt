@file:OptIn(ExperimentalMaterial3Api::class)

package uz.mobile.mytaxitask.presentation.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.mobile.mytaxitask.common.util.isExpanded
import uz.mobile.mytaxitask.common.util.valid
import uz.mobile.mytaxitask.domain.use_cases.GetUserLatestLocation

class HomeViewModel(
    private val userLatestLocation: GetUserLatestLocation
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeState())
    val uiState: StateFlow<HomeState> = _uiState.asStateFlow()

    private val _sideEffects = MutableSharedFlow<HomeSideEffects>()
    val sideEffects: SharedFlow<HomeSideEffects> = _sideEffects

    fun dispatch(intent: HomeIntent) {
        when (intent) {
            HomeIntent.ChevronsUp -> {

            }

            HomeIntent.NavigateToBordur -> {
                viewModelScope.launch {
                    _sideEffects.emit(HomeSideEffects.NavigateToBordur)
                }
            }

            HomeIntent.NavigateToOrders -> {
                viewModelScope.launch {
                    _sideEffects.emit(HomeSideEffects.NavigateToOrders)
                }
            }

            HomeIntent.NavigateToTariff -> {
                viewModelScope.launch {
                    _sideEffects.emit(HomeSideEffects.NavigateToTariff)
                }
            }

            is HomeIntent.OnSpeedChange -> {
                _uiState.value = _uiState.value.copy(speed = intent.speed)
            }

            is HomeIntent.OnZoomOut -> {
                val currentMapState = _uiState.value.mapState
                val currentZoom = _uiState.value.mapState.zoom
                if (currentZoom > 0) {
                    _uiState.value =
                        _uiState.value.copy(mapState = currentMapState.copy(zoom = currentZoom - 1))
                }
            }

            is HomeIntent.OnZoomIn -> {
                val currentMapState = _uiState.value.mapState
                val currentZoom = _uiState.value.mapState.zoom
                if (currentZoom < 18) {
                    _uiState.value =
                        _uiState.value.copy(mapState = currentMapState.copy(zoom = currentZoom + 1))
                }
            }

            HomeIntent.OpenSideBar -> {
                viewModelScope.launch {
                    _sideEffects.emit(HomeSideEffects.OpenSideBar)
                }
            }

            is HomeIntent.OnTabSelectionChanged -> {
                val currentTabState = _uiState.value.tabState
                _uiState.value =
                    _uiState.value.copy(tabState = currentTabState.copy(selectedTabIndex = intent.selectedTabIndex))
            }

            HomeIntent.BottomSheetExpanded -> {
                _uiState.value = _uiState.value.copy(mapActionButtonVisible = false)
            }
            HomeIntent.BottomSheetPartiallyExpanded -> {
                _uiState.value = _uiState.value.copy(mapActionButtonVisible = true)
            }
        }
    }

    private fun getUserLocationUpdates() {
        userLatestLocation.invoke()
            .catch { e ->
                e.printStackTrace()
            }
            .onEach { location ->
                location?.let {
                    if (location.valid()) {
                        val userLocation =
                            UserLocation(
                                longitude = location.longitude!!,
                                latitude = location.latitude!!
                            )
                        val currentMapState = _uiState.value.mapState

                        _uiState.value = _uiState.value.copy(
                            mapState = currentMapState.copy(userLiveLocation = userLocation)
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }

    init {
        getUserLocationUpdates()
    }
}