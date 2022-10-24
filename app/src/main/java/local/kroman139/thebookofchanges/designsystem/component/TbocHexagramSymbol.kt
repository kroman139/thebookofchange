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

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme

@Composable
fun TbocHexagramSymbol(
    rawStrokes: List<Boolean>,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    highlightIndex: Int? = null,
) {
    val highlightColor = color.takeOrElse {
        LocalTextStyle.current.color.takeOrElse {
            LocalContentColor.current
        }
    }

    val strokeColor = if (highlightIndex == null) {
        highlightColor
    } else {
        highlightColor.copy(alpha = 0.3f)
    }

    Canvas(
        modifier = modifier,
    ) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val fullStrokeSize = Size(
            width = canvasWidth,
            height = canvasHeight / 11.0f
        )
        val partStrokeSize = Size(
            width = canvasWidth / 3.0f,
            height = canvasHeight / 11.0f
        )

        rawStrokes.forEachIndexed { idx, isSolid ->
            val index = rawStrokes.size - idx - 1
            val yOffset = index * 2.0f * fullStrokeSize.height

            val color = if (idx == highlightIndex) {
                highlightColor
            } else {
                strokeColor
            }

            if (isSolid) {
                drawRect(
                    color = color,
                    topLeft = Offset(x = 0.0f, y = yOffset),
                    size = fullStrokeSize,
                )
            } else {
                drawRect(
                    color = color,
                    topLeft = Offset(x = 0.0f, y = yOffset),
                    size = partStrokeSize,
                )
                drawRect(
                    color = color,
                    topLeft = Offset(x = canvasWidth - partStrokeSize.width, y = yOffset),
                    size = partStrokeSize,
                )
            }
        }
    }
}

@Preview
@Composable
fun HexagramSymbolPreview() {
    TbocTheme {
        Surface(
            modifier = Modifier,
        ) {
            TbocHexagramSymbol(
                rawStrokes = listOf(true, false, true, false, false, true),
                modifier = Modifier
                    .padding(all = 16.dp)
                    .size(64.dp),
            )
        }
    }
}

@Preview
@Composable
fun HexagramSymbolPreview2() {
    TbocTheme {
        Surface(
            modifier = Modifier,
        ) {
            TbocHexagramSymbol(
                rawStrokes = listOf(true, false, true, false, false, true),
                modifier = Modifier
                    .padding(all = 16.dp)
                    .size(64.dp),
                highlightIndex = 2,
            )
        }
    }
}