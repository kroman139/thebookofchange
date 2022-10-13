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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.HexagramSymbol
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.previewHexagrams
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState
import local.kroman139.thebookofchanges.ui.utils.toUiState

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
    HexagramView(
        hexagramUi = hexagramUiState,
        modifier = Modifier
            .fillMaxSize(),
    )
}

@Composable
internal fun HexagramView(
    hexagramUi: HexagramUiState,
    modifier: Modifier = Modifier,
) {
    val hexagram = hexagramUi.hexagram

    Surface(
        modifier = modifier.background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp)
        ) {
            Row {
                HexagramSymbol(
                    rawStrokes = hexagramUi.rawStrokes,
                    modifier = Modifier.size(32.dp),
                )
                Text(
                    text = "${hexagram.logogram}   ${hexagram.title}",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp),
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
                        Column {
                            HexagramSymbol(
                                rawStrokes = hexagramUi.rawStrokes,
                                modifier = Modifier
                                    .padding(top = 8.dp)
                                    .size(32.dp),
                                highlightIndex = i,
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

//                            val myId = "inlineContent"
//
//                            val inlineContent = mapOf(
//                                Pair(
//                                    // This tells the [BasicText] to replace the placeholder string "[myBox]" by
//                                    // the composable given in the [InlineTextContent] object.
//                                    myId,
//                                    InlineTextContent(
//                                        // Placeholder tells text layout the expected size and vertical alignment of
//                                        // children composable.
//                                        Placeholder(
//                                            width = 2.em,
//                                            height = 2.em,
//                                            placeholderVerticalAlign = PlaceholderVerticalAlign.TextTop,
//                                        )
//                                    ) {
//                                        // This [Box] will fill maximum size, which is specified by the [Placeholder]
//                                        // above. Notice the width and height in [Placeholder] are specified in TextUnit,
//                                        // and are converted into pixel by text layout.
//                                        HexagramSymbol(
//                                            rawStrokes = hexagramUi.rawStrokes,
//                                            modifier = Modifier.fillMaxSize(), //Modifier.size(32.dp),
//                                            highlightIndex = i,
//                                        )
//                                        //Box(modifier = Modifier.fillMaxSize().background(color = Color.Red))
//                                    }
//                                )
//                            )
//
//                            Text(
//                                //text = "${stroke.text}",
//                                modifier = Modifier.padding(vertical = 8.dp),
//                                text = buildAnnotatedString {
//                                    appendInlineContent(myId, "[myBox]")
//                                    append("${stroke.text}")
//                                },
//                                inlineContent = inlineContent,
//                                style = LocalTextStyle.current.copy(
//                                    platformStyle = PlatformTextStyle(
//                                        includeFontPadding = false
//                                    )
//                                )
//                            )
                        }
                    } else {
                        Row(modifier = Modifier.padding(top = 8.dp)) {
                            HexagramSymbol(
                                rawStrokes = hexagramUi.rawStrokes,
                                modifier = Modifier
                                    .padding(top = 7.dp)
                                    .size(32.dp),
                                highlightIndex = i,
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
                }
            }
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
            HexagramView(
                hexagramUi = hexagram.toUiState(),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            )
        }
    }
}
