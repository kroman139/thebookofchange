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

import android.util.Log
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.isUnspecified
import local.kroman139.thebookofchanges.model.data.Hexagram

@Composable
fun TbocHexaLogogram(
    hexagram: Hexagram,
    modifier: Modifier = Modifier,
    showEmptyLine: Boolean = true,
    fontSize: TextUnit = TextUnit.Unspecified,
) = Text(
    text = buildAnnotatedString {
        val logogram = hexagram.logogram

        append(logogram[0])

        // Some logograms have 1 char, other logos have 2 chars.
        // Let's add empty string for all with 1 char to get identical height
        // for every card.
        if (showEmptyLine || logogram.length > 1) {
            append("\n")
        }

        if (logogram.length > 1) {
            append(logogram[1])
        }

        // Just to log the unpredicted option
        if (logogram.length > 2) {
            Log.e(
                "unpredicted-error",
                "Unpredicted logogram length ${logogram.length}, logogram is '$logogram'"
            )
        }
    },
    modifier = modifier,
    textAlign = TextAlign.Center,
    fontSize = fontSize,
    lineHeight = if (fontSize.isUnspecified) {
        fontSize
    } else {
        fontSize.times(1.2f)
    },
)