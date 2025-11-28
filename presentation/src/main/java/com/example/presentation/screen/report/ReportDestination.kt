package com.example.presentation.screen.report

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.presentation.utils.nav.ScreenDestinations

fun NavGraphBuilder.reportDestination(navController: NavController) {
    composable(
        route = ScreenDestinations.Report.route,
//        arguments = listOf(
//            navArgument(name = "") {
//                type = NavType.LongType
//                defaultValue = 0L
//            }
//        ) -> if route contains arguments
    ) {
        val viewModel: ReportViewModel = hiltViewModel()

        val argument: ReportArgument = let {
            val state by viewModel.state.collectAsStateWithLifecycle()

            ReportArgument(
                state = state,
                intent = viewModel::onIntent,
                event = viewModel.eventFlow
            )
        }

        val data: ReportData = let {

            ReportData.empty()
        }

        ReportScreen(
            navController = navController,
            argument = argument,
            data = data
        )
    }
}