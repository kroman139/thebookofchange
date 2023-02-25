/*
 * Copyright (c) 2022-2023 kroman139
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
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.model.data.previewHexagrams

@Composable
fun TbocHexagramView(
    hexagram: Hexagram,
    modifier: Modifier = Modifier,
    showEmptyLineLogogram: Boolean = true,
) {
    Column(modifier = modifier) {
        val fontSizeDp: Dp
        val lineHeightDp: Dp

        with(LocalDensity.current) {
            val textStyle = LocalTextStyle.current
            fontSizeDp = textStyle.fontSize.toDp()
            lineHeightDp = textStyle.lineHeight.toDp()
        }

        val symbolHeight = fontSizeDp + lineHeightDp
        val strokesPadding = lineHeightDp // + fontSizeDp

        val logogramPadding = fontSizeDp

        HexaHeader(
            hexagram = hexagram,
            symbolHeight = lineHeightDp,
            logogramPadding = logogramPadding,
            showEmptyLine = showEmptyLineLogogram,
        )

        Text(
            text = buildAnnotatedString {
                append(hexagram.text)
                append(" ")
                withStyle(SpanStyle(fontStyle = FontStyle.Italic)) {
                    append(hexagram.summary)
                }
            },
            modifier = Modifier.padding(top = strokesPadding),
        )

        val symbolPaddingTop = (lineHeightDp - fontSizeDp) / 2
        val symbolPaddingEnd = fontSizeDp * 2 / 3

        val textPaddingTop = lineHeightDp - fontSizeDp

        val rowModifier = Modifier.padding(top = strokesPadding)

        hexagram.strokes.forEachIndexed { i, stroke ->
            BoxWithConstraints {
                if (maxWidth < 380.dp) {
                    NarrowStrokeView(
                        hexagram.symbolStrokes,
                        i,
                        stroke,
                        symbolHeight = symbolHeight,
                        textPaddingTop = textPaddingTop,
                        modifier = rowModifier,
                    )
                } else {
                    WideStrokeView(
                        hexagram.symbolStrokes,
                        i,
                        stroke,
                        symbolHeight = symbolHeight,
                        symbolPaddingTop = symbolPaddingTop,
                        symbolPaddingEnd = symbolPaddingEnd,
                        modifier = rowModifier,
                    )
                }
            }
        }
    }
}

@Composable
private fun HexaHeader(
    hexagram: Hexagram,
    symbolHeight: Dp,
    logogramPadding: Dp,
    modifier: Modifier = Modifier,
    showEmptyLine: Boolean = true,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        TbocHexagramSymbol(
            rawStrokes = hexagram.symbolStrokes,
            modifier = Modifier
                .size(symbolHeight * 12 / 10),
        )

        TbocHexaLogogram(
            hexagram = hexagram,
            fontSize = MaterialTheme.typography.headlineMedium.fontSize,
            modifier = Modifier
                .padding(start = logogramPadding),
            showEmptyLine = showEmptyLine,
        )
    }
}

@Composable
private fun NarrowStrokeView(
    rawStrokes: List<Boolean>,
    idx: Int,
    stroke: HexagramStroke,
    symbolHeight: Dp,
    textPaddingTop: Dp,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier) {
        TbocHexagramSymbol(
            rawStrokes = rawStrokes,
            modifier = Modifier.size(symbolHeight),
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
            modifier = Modifier.padding(top = textPaddingTop),
        )
    }
}

@Composable
private fun WideStrokeView(
    rawStrokes: List<Boolean>,
    idx: Int,
    stroke: HexagramStroke,
    symbolHeight: Dp,
    symbolPaddingTop: Dp,
    symbolPaddingEnd: Dp,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier) {
        TbocHexagramSymbol(
            rawStrokes = rawStrokes,
            modifier = Modifier
                .padding(top = symbolPaddingTop, end = symbolPaddingEnd)
                .size(symbolHeight),
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
        )
    }
}

@DevicePreviews
@Composable
fun TbocHexagramViewPreview() {
    TbocTheme {
        Surface {
            TbocHexagramView(
                hexagram = previewHexagrams[0],
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(all = 16.dp),
                showEmptyLineLogogram = false,
            )
        }
    }
}