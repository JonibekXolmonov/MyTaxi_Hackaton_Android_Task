@file:OptIn(ExperimentalMaterial3Api::class)

package uz.mobile.mytaxitask.presentation.ui.screens.home

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetValue

sealed class HomeIntent {
    data object OpenSideBar : HomeIntent()
    data class OnTabSelectionChanged(val selectedTabIndex: Int) : HomeIntent()
    data class OnSpeedChange(val speed: Int) : HomeIntent()
    data object OnZoomIn : HomeIntent()
    data object OnZoomOut : HomeIntent()
    data object ChevronsUp : HomeIntent()
    data object NavigateToTariff : HomeIntent()
    data object NavigateToOrders : HomeIntent()
    data object NavigateToBordur : HomeIntent()
    data object BottomSheetExpanded : HomeIntent()
    data object BottomSheetPartiallyExpanded : HomeIntent()
}