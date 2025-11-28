package com.example.presentation.screen.diagnosis

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.utils.nav.ScreenDestinations

fun NavGraphBuilder.diagnosisDestination(navController: NavController) {
    composable(
        route = ScreenDestinations.Diagnosis.route,
//        arguments = listOf(
//            navArgument(name = "") {
//                type = NavType.LongType
//                defaultValue = 0L
//            }
//        ) -> if route contains arguments
    ) {
        val viewModel: DiagnosisViewModel = hiltViewModel()

        val argument: DiagnosisArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            DiagnosisArgument(
                state = state,
                intent = viewModel::onIntent,
                event = viewModel.eventFlow
            )
        }

        val data: DiagnosisData = let {

            DiagnosisData.empty()
        }

        DiagnosisScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}