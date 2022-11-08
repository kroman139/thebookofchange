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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.TbocHexagramSymbol
import local.kroman139.thebookofchanges.designsystem.component.TbocBackButton
import local.kroman139.thebookofchanges.designsystem.component.TbocHexagramView
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.model.data.previewHexagrams
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState
import local.kroman139.thebookofchanges.ui.utils.toUiStateOk

@Composable
fun HexagramRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HexagramViewModel = hiltViewModel(),
) {
    val uiState: HexagramUiState by viewModel.hexagramUiState.collectAsState()

    HexagramScreen(
        uiState = uiState,
        navigateBack = navigateBack,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HexagramScreen(
    uiState: HexagramUiState,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when(uiState) {
        is HexagramUiState.Ok -> {
            Column {

                val hexagram = uiState.hexagram

                Row {
                    TbocBackButton(
                        onClick = navigateBack,
                    )

                    Text(
                        text = "${hexagram.id}. ${hexagram.logogram} ${hexagram.title}",
                        style = MaterialTheme.typography.titleLarge,
                        modifier = Modifier.padding(start = 16.dp),
                    )

                    TbocHexagramSymbol(
                        rawStrokes = uiState.rawStrokes,
                        modifier = Modifier.size(32.dp),
                    )
                }

                TbocHexagramView(
                    hexagramUiOk = uiState,
                    modifier = modifier
                        .fillMaxSize(),
                )

            }
        }
        else -> {
            // TODO: show empty screen or something else?
        }
    }
}


@DevicePreviews
@Composable
fun HexagramViewPreview() {
    val hexagram = previewHexagrams[0]

    TbocTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            TbocHexagramView(
                hexagramUiOk = hexagram.toUiStateOk(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            )
        }
    }
}
