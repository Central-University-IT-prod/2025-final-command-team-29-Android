package ru.prodcontest.crazypeppers.tloyalty.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed class AppGraph {

    @Serializable
    data object Customer : AppGraph()

    @Serializable
    data object Partner : AppGraph()
}
