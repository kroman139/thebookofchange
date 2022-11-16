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

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.*
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.model.data.previewHexagrams

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
        navigateToHexagram = navigateToHexagram,
        modifier = modifier,
    )
}

@Composable
fun HexaLibraryScreen(
    uiState: HexaLibraryUiState,
    navigateBack: () -> Unit,
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
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
    ) {
        LibraryListView(
            hexaList = uiStateLibrary.hexaList,
            navigateToHexagram = navigateToHexagram,
        )
    }
}

@Composable
fun LibraryListView(
    hexaList: List<Hexagram>,
    navigateToHexagram: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(
            items = hexaList,
            key = { it.id },
        ) {
            HexagramCard(
                hexagram = it,
                openHexagram = { navigateToHexagram(it.id) },
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            )
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
        onClick = openHexagram,
        modifier = modifier,
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TbocHexagramSymbol(
                rawStrokes = hexagram.symbolStrokes,
                modifier = Modifier
                    .padding(all = 8.dp)
                    .size(32.dp)
                    .align(alignment = CenterVertically),
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                        append("${hexagram.id}. ")
                    }
                    append(hexagram.title)
                },
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp)
                    .weight(weight = 1.0f),
            )

            TbocHexaLogogram(
                hexagram = hexagram,
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}

@DevicePreviews
@Composable
fun HexagramCardPreview() {
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