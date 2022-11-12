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

package local.kroman139.thebookofchanges.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.model.data.previewHexagrams

@Composable
fun TbocHexagramView(
    hexagram: Hexagram,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {

        HexaHeader(hexagram = hexagram)

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
                    NarrowStrokeView(hexagram.symbolStrokes, i, stroke)
                } else {
                    WideStrokeView(hexagram.symbolStrokes, i, stroke)
                }
            }
        }
    }
}

@Composable
private fun HexaHeader(
    hexagram: Hexagram,
    modifier: Modifier = Modifier,
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
            modifier = Modifier.padding(start = 8.dp, top = 8.dp, end = 8.dp),
            textAlign = TextAlign.Center,
        )

    }
}


@Composable
private fun NarrowStrokeView(
    rawStrokes: List<Boolean>,
    idx: Int,
    stroke: HexagramStroke
) {
    Column {
        TbocHexagramSymbol(
            rawStrokes = rawStrokes,
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
    rawStrokes: List<Boolean>,
    idx: Int,
    stroke: HexagramStroke
) {
    Row(modifier = Modifier.padding(top = 8.dp)) {
        TbocHexagramSymbol(
            rawStrokes = rawStrokes,
            modifier = Modifier
//                .padding(top = 7.dp)
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

@DevicePreviews
@Composable
fun TbocHexagramViewPreview() {
    val hexagram = previewHexagrams[0]

    TbocTheme {
        Surface(
            modifier = Modifier
            // .fillMaxSize(),
        ) {
            TbocHexagramView(
                hexagram = previewHexagrams[0],
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(all = 16.dp),
            )
        }
    }
}