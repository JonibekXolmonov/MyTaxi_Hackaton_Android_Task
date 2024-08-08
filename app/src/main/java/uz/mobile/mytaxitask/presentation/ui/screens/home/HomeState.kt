package uz.mobile.mytaxitask.presentation.ui.screens.home

import uz.mobile.mytaxitask.common.tabList

data class HomeState(
    val isLoading: Boolean = false,
    val speed: Int = 95,
    val tabState: TabState = TabState(),
    val mapState: MapState = MapState(),
    val error: String? = null,
    val mapActionButtonVisible: Boolean = true
)

data class TabState(
    val tabs: List<String> = tabList,
    val selectedTabIndex: Int = 1
)

data class MapState(
    val zoom: Double = 12.0,
    val userLiveLocation: UserLocation? = null
)

data class UserLocation(
    val longitude: Double = 69.2401,
    var latitude: Double = 41.2995
)