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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.*
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.model.data.previewHexagrams
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
    TbocScreen(
        navigateBack = navigateBack,
        titleText = stringResource(R.string.hexalibrary_screen_title),
        modifier = modifier,
    ) {
        when (uiState) {
            is HexaLibraryUiState.Library -> {
                LibraryView(
                    uiStateLibrary = uiState,
                    switchMode = switchMode,
                    navigateToHexagram = navigateToHexagram,
                    modifier = modifier,
                )
            }
            else -> {
                // TODO: What to do?
            }
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
        ) {

            Row(
                modifier = Modifier.align(alignment = CenterHorizontally),
            ) {
                TbocDummyButton(
                    text = "compact",
                    enabled = (uiStateLibrary.viewMode != ViewMode.COMPACT),
                    onClick = { switchMode(ViewMode.COMPACT) },
                )

                DummyText("*", modifier = Modifier.padding(start = 16.dp, end = 16.dp))

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

    if (true) {
        LazyColumn(
            modifier = modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        ) {
            items(
                items = hexagramListUiStateOk,
                key = { it.hexagram.id },
            ) {
                HexagramCard(
                    hexagram = it.hexagram,
                    openHexagram = { navigateToHexagram(it.hexagram.id) },
                    modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = CenterHorizontally,
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
}

@Composable
fun HexagramCard(
    hexagram: Hexagram,
    openHexagram: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TbocOutlinedCard(
        onTap = { openHexagram() },
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Column {
                TbocHexagramSymbol(
                    rawStrokes = hexagram.symbolStrokes,
                    modifier = Modifier
                        .padding(all = 8.dp)
                        .size(32.dp),
                )

                // TODO: remove this debug text
//                Text(
//                    text = hexagram.symbol,
//                    color = MaterialTheme.colorScheme.outlineVariant,
//                    style = MaterialTheme.typography.titleLarge,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
//                )
            }

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${hexagram.id}. ")
                    }
                    append(hexagram.title)
                },
                modifier = Modifier
                    .padding(top = 8.dp)
                    .weight(weight = 1.0f),
            )

            Text(
                text = buildAnnotatedString {
                    hexagram.logogram.forEachIndexed() { idx, char ->
                        if (idx > 0) {
                            append("\n")
                        }
                        append(char)
                    }
                },
                modifier = Modifier.padding(all = 8.dp),
                textAlign = TextAlign.Center,
            )

        }
    }
}

@DevicePreviews
@Composable
fun HexagramCard() {
    val hexagram = previewHexagrams[0]

    TbocTheme {
        Surface(
            modifier = Modifier
            // .fillMaxSize(),
        ) {
            Column {
                HexagramCard(
                    hexagram = previewHexagrams[0],
                    openHexagram = {},
                    modifier = Modifier.padding(all = 8.dp)
                )

                HexagramCard(
                    hexagram = previewHexagrams[1],
                    openHexagram = {},
                    modifier = Modifier.padding(all = 8.dp)
                )
            }
        }
    }
}