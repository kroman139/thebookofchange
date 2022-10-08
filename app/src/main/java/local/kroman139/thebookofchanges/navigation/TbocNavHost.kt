/*
 * Copyright 2022 kroman139
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       https://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package local.kroman139.thebookofchanges.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import local.kroman139.thebookofchanges.ui.hexagram.HexagramRoute
import local.kroman139.thebookofchanges.ui.hexagram.navigation.HexagramDestination
import local.kroman139.thebookofchanges.ui.home.HomeRoute
import local.kroman139.thebookofchanges.ui.home.navigation.HomeDestination
import local.kroman139.thebookofchanges.ui.home.navigation.homeGraph

@Composable
fun TbocNavHost(
    navController: NavHostController,
    onNavigateToDestination: (TbocNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        homeGraph(
            navigateToHexagram = {
                onNavigateToDestination(
                    HexagramDestination, HexagramDestination.createNavigationRoute(it)
                )
            }
        )
        composable(route = HomeDestination.route) {
            HomeRoute(
                navigateToHexagram = {
                    onNavigateToDestination(
                        HexagramDestination, HexagramDestination.createNavigationRoute(it)
                    )
                }
            )
        }
        composable(
            route = HexagramDestination.route,
            arguments = listOf(
                navArgument(HexagramDestination.hexagramIdArg) { type = NavType.StringType }
            )
        ) {
            HexagramRoute(
                onBackClick = onBackClick
            )
        }
    }
}