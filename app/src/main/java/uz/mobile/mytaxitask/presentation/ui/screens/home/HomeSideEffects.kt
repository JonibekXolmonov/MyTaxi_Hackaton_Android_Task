package uz.mobile.mytaxitask.presentation.ui.screens.home

sealed class HomeSideEffects {
    data class ShowError(val message: String) : HomeSideEffects()
    data object NavigateToTariff : HomeSideEffects()
    data object NavigateToOrders : HomeSideEffects()
    data object NavigateToBordur : HomeSideEffects()
    data object OpenSideBar : HomeSideEffects()
    data object Nothing : HomeSideEffects()
}