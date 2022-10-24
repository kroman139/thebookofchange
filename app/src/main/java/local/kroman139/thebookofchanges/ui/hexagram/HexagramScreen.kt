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
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.model.data.previewHexagrams
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState
import local.kroman139.thebookofchanges.ui.utils.toUiStateOk

@Composable
fun HexagramRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HexagramViewModel = hiltViewModel(),
) {
    val uiState: HexagramUiState by viewModel.hexagramUiState.collectAsState()

    HexagramScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HexagramScreen(
    uiState: HexagramUiState,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when(uiState) {
        is HexagramUiState.Ok -> {
            HexagramView(
                hexagramUiOk = uiState,
                onBackClick = onBackClick,
                modifier = modifier
                    .fillMaxSize(),
            )
        }
        else -> {
            // TODO: show empty screen or something else?
        }
    }
}

@Composable
internal fun HexagramView(
    hexagramUiOk: HexagramUiState.Ok,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val hexagram = hexagramUiOk.hexagram

    Surface(
        modifier = modifier.background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {
            Row {
                TbocBackButton(
                    onClick = onBackClick,
                )

                Text(
                    text = "${hexagram.id}. ${hexagram.logogram} ${hexagram.title}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )

                TbocHexagramSymbol(
                    rawStrokes = hexagramUiOk.rawStrokes,
                    modifier = Modifier.size(32.dp),
                )
            }

            Text(
                text = buildAnnotatedString {
                    append(hexagram.text)
                    append(" ")
                    withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                        append(hexagram.summary)
                    }
                },
                modifier = Modifier.padding(vertical = 8.dp),
            )

            hexagram.strokes.forEachIndexed { i, stroke ->
                BoxWithConstraints {
                    if (maxWidth < 380.dp) {
                        NarrowStrokeView(hexagramUiOk, i, stroke)
                    } else {
                        WideStrokeView(hexagramUiOk, i, stroke)
                    }
                }
            }
        }
    }
}

@Composable
private fun NarrowStrokeView(
    hexagramUiOk: HexagramUiState.Ok,
    idx: Int,
    stroke: HexagramStroke
) {
    Column {
        TbocHexagramSymbol(
            rawStrokes = hexagramUiOk.rawStrokes,
            modifier = Modifier
                .padding(top = 8.dp)
                .size(32.dp),
            highlightIndex = idx,
        )

        Text(
            text = buildAnnotatedString {
                append(stroke.text)
                append(" ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(stroke.summary)
                }
            },
            modifier = Modifier.padding(vertical = 8.dp),
        )
    }
}

@Composable
private fun WideStrokeView(
    hexagramUiOk: HexagramUiState.Ok,
    idx: Int,
    stroke: HexagramStroke
) {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        TbocHexagramSymbol(
            rawStrokes = hexagramUiOk.rawStrokes,
            modifier = Modifier
                .padding(top = 7.dp)
                .size(32.dp),
            highlightIndex = idx,
        )

        Text(
            text = buildAnnotatedString {
                append(stroke.text)
                append(" ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(stroke.summary)
                }
            },
            modifier = Modifier.padding(horizontal = 8.dp),
        )
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
            HexagramView(
                hexagramUiOk = hexagram.toUiStateOk(),
                onBackClick = { },
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            )
        }
    }
}
