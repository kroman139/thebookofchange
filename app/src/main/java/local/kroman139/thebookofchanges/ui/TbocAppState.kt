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

package local.kroman139.thebookofchanges.ui

import android.util.Log
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import local.kroman139.thebookofchanges.navigation.TbocNavigationDestination

@Composable
fun rememberTbocAppState(
    windowSizeClass: WindowSizeClass,
    navController: NavHostController = rememberNavController()
): TbocAppState {

    return remember(navController, windowSizeClass) {
        TbocAppState(navController, windowSizeClass)
    }
}


@Stable
class TbocAppState(
    val navController: NavHostController,
    val windowSizeClass: WindowSizeClass
) {
    val currentDestination: NavDestination?
        @Composable get() = navController
            .currentBackStackEntryAsState().value?.destination

    val compactDevice: Boolean
        get() = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Compact ||
                windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    fun navigate(destination: TbocNavigationDestination, route: String? = null) {
        Log.d("AppState", "navigate: destination = $destination, route = $route")

        navController.navigate(route ?: destination.route)
    }

    fun navigateFromHome(destination: TbocNavigationDestination, route: String? = null) {
        Log.d("AppState", "navigateFromHome: destination = $destination, route = $route")

        navController.navigate(route ?: destination.route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }
        }
    }

    fun navigateBack() {
        navController.popBackStack()
    }
}