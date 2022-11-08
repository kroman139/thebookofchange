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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState

@Composable
internal fun TbocHexagramView(
    hexagramUiOk: HexagramUiState.Ok,
    modifier: Modifier = Modifier,
) {
    val hexagram = hexagramUiOk.hexagram

    Column(
        modifier = modifier,
    ) {
//        Row {
//            TbocBackButton(
//                onClick = navigateBack,
//            )
//
//            Text(
//                text = "${hexagram.id}. ${hexagram.logogram} ${hexagram.title}",
//                style = MaterialTheme.typography.titleLarge,
//                modifier = Modifier.padding(start = 16.dp),
//            )
//
//            TbocHexagramSymbol(
//                rawStrokes = hexagramUiOk.rawStrokes,
//                modifier = Modifier.size(32.dp),
//            )
//        }

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
