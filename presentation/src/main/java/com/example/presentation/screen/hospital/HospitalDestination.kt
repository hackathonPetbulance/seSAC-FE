package com.example.presentation.screen.hospital

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.utils.nav.ScreenDestinations

fun NavGraphBuilder.hospitalDestination(navController: NavController) {
    composable(
        route = ScreenDestinations.Hospital.route,
//        arguments = listOf(
//            navArgument(name = "") {
//                type = NavType.LongType
//                defaultValue = 0L
//            }
//        ) -> if route contains arguments
    ) {
        val viewModel: HospitalViewModel = hiltViewModel()

        val argument: HospitalArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            HospitalArgument(
                state = state,
                intent = viewModel::onIntent,
                event = viewModel.eventFlow
            )
        }

        val data: HospitalData = let {

            HospitalData.empty()
        }

        HospitalScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}