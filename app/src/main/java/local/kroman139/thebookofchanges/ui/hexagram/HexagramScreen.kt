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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.DummyText
import local.kroman139.thebookofchanges.designsystem.component.HexagramSymbol
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.model.data.previewHexagrams

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
        text = "hexagram screen! Number ${hexagramUiState.hexagram.id}",
        modifier = modifier,
    )
}

@Composable
internal fun HexagramView(
    hexagram: Hexagram,
    modifier: Modifier = Modifier,
) {
    val rawStrokes = hexagram.getRawStrokes()

    Surface(
        modifier = modifier.background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
        ) {
            Row {
                HexagramSymbol(
                    rawStrokes = rawStrokes,
                    modifier = Modifier.size(32.dp),
                )
                Text(
                    text = "${hexagram.logogram}   ${hexagram.title}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }

            Text(
                text = "${hexagram.description}",
                modifier = Modifier.padding(vertical = 8.dp),
            )

            hexagram.strokes.forEachIndexed { i, stroke ->
                HexagramSymbol(
                    rawStrokes = rawStrokes,
                    modifier = Modifier.size(32.dp),
                    highlightIndex = i,
                )

                Text(
                    text = "${stroke.text}",
                    modifier = Modifier.padding(vertical = 8.dp),
                )
            }
        }
    }
}

private fun Hexagram.getRawStrokes() =
    strokes.map {
        it.solidLine
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
                hexagram = hexagram,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            )
        }
    }

}
