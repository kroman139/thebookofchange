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

package local.kroman139.thebookofchanges.ui.hexagram

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DummyText

@Composable
fun HexagramRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HexagramViewModel = hiltViewModel(),
) {
    val uiState: HexagramUiState by viewModel.hexagramUiState.collectAsState()

    HexagramScreen(
        hexagramUiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@Composable
internal fun HexagramScreen(
    hexagramUiState: HexagramUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    DummyText(
        text = "hexagram screen! Number ${hexagramUiState.hexagramNumber}",
        modifier = modifier,
    )
}