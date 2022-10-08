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

package local.kroman139.thebookofchanges.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.DummyButton
import local.kroman139.thebookofchanges.designsystem.component.DummyText
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme

@Composable
fun HomeRoute(
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    HomeScreen(
        navigateToHexagram = navigateToHexagram,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        DummyText(text = "Home screen, 乾, ䷀")

        DummyButton(
            text = "open hexagram",
            onClick = { navigateToHexagram("1") }
        )
    }
}

@DevicePreviews
@Composable
fun HomeScreenPreview() {
    TbocTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                navigateToHexagram = { _ -> },
            )
        }
    }
}