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

package local.kroman139.thebookofchanges.ui.hexalibrary

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.*
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState

@Composable
fun HexaLibraryRoute(
    navigateBack: () -> Unit,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HexaLibraryViewModel = hiltViewModel(),
) {
    val uiState by viewModel.hexaLibraryUiState.collectAsState()

    HexaLibraryScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        switchMode = viewModel::switchViewMode,
        navigateToHexagram = navigateToHexagram,
        modifier = modifier,
    )
}

@Composable
fun HexaLibraryScreen(
    uiState: HexaLibraryUiState,
    navigateBack: () -> Unit,
    switchMode: (ViewMode) -> Unit,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (uiState) {
        is HexaLibraryUiState.Library -> {
            TbocScreen(
                navigateBack = navigateBack,
                modifier = modifier,
                titleText = "Answer",
            ) {
                LibraryView(
                    uiStateLibrary = uiState,
                    switchMode = switchMode,
                    navigateToHexagram = navigateToHexagram,
                    modifier = modifier,
                )
            }
        }
        else -> {
            // TODO: What to do?
        }
    }
}

@Composable
fun LibraryView(
    uiStateLibrary: HexaLibraryUiState.Library,
    switchMode: (ViewMode) -> Unit,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {

            Row(
                modifier = Modifier.align(alignment = CenterHorizontally),
            ) {
                TbocDummyButton(
                    text = "compact",
                    enabled = (uiStateLibrary.viewMode != ViewMode.COMPACT),
                    onClick = { switchMode(ViewMode.COMPACT) },
                )
                DummyText(" | ")
                TbocDummyButton(
                    text = "list",
                    enabled = (uiStateLibrary.viewMode != ViewMode.LIST),
                    onClick = { switchMode(ViewMode.LIST) },
                )
            }

            when (uiStateLibrary.viewMode) {
                ViewMode.COMPACT -> {
                    LibraryCompactView(
                        hexagramListUiStateOk = uiStateLibrary.hexaList,
                        navigateToHexagram = navigateToHexagram,
                    )
                }
                ViewMode.LIST -> {
                    LibraryListView(
                        hexagramListUiStateOk = uiStateLibrary.hexaList,
                        navigateToHexagram = navigateToHexagram,
                    )
                }
            }
        }
    }
}

@Composable
fun LibraryCompactView(
    hexagramListUiStateOk: List<HexagramUiState.Ok>,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    DummyText("compact view")

}

@Composable
fun LibraryListView(
    hexagramListUiStateOk: List<HexagramUiState.Ok>,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    // DummyText("list view")

    Column(
//        modifier = modifier
//            .fillMaxSize()
//            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        hexagramListUiStateOk.forEach {
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                TbocHexagramSymbol(
                    rawStrokes = it.rawStrokes,
                    modifier = Modifier.size(32.dp),
                )
                Text(
                    text = "${it.hexagram.symbol}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp),
                )
                TbocDummyButton(
                    text = "${it.hexagram.id}. ${it.hexagram.title}",
                    modifier = Modifier.padding(start = 8.dp),
                    onClick = { navigateToHexagram(it.hexagram.id) }
                )
                Text(
                    text = "${it.hexagram.logogram}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}