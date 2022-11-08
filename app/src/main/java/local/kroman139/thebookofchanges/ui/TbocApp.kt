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

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.navigation.TbocNavHost

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalLayoutApi::class
)
@Composable
fun TbocApp(
    windowSizeClass: WindowSizeClass,
    appState: TbocAppState = rememberTbocAppState(windowSizeClass)
) {
    TbocTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            Scaffold(
                //containerColor = Color.Transparent,
                // contentColor = MaterialTheme.colorScheme.onBackground,
            ) { padding ->
                TbocNavHost(
                    navController = appState.navController,
                    navigateBack = appState::navigateBack,
                    navigate = appState::navigate,
                    navigateFromHome = appState::navigateFromHome,
                    modifier = Modifier
                        .padding(padding)
                        .fillMaxSize()
                        .consumedWindowInsets(padding)
                )
            }
        }
    }
}