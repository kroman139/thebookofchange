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

package local.kroman139.thebookofchanges.ui.answerslist

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.datetime.Clock
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.*
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Answer
import local.kroman139.thebookofchanges.model.data.previewHexagrams

@Composable
fun AnswersListRoute(
    navigateBack: () -> Unit,
    openAnswer: (Long) -> Unit,
    getAnswer: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnswersListViewModel = hiltViewModel(),
) {

    val answerUiState by viewModel.answersUiState.collectAsState()

    AnswersListScreen(
        answerUiState = answerUiState,
        navigateBack = navigateBack,
        openAnswer = openAnswer,
        deleteAnswer = viewModel::deleteAnswer,
        getAnswer = getAnswer,
        modifier = modifier,
    )
}

@Composable
fun AnswersListScreen(
    answerUiState: AnswerUiState,
    navigateBack: () -> Unit,
    openAnswer: (Long) -> Unit,
    deleteAnswer: (Long) -> Unit,
    getAnswer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TbocScreen(
        navigateBack = navigateBack,
        titleText = stringResource(R.string.answers_list_screen_title),
        modifier = modifier,
    ) {
        when (answerUiState) {
            is AnswerUiState.Loading -> Unit // TODO: implement this
            is AnswerUiState.Success -> {
                if (answerUiState.briefInfo.isEmpty()) {
                    EmptyAnswersList(
                        getAnswer = getAnswer,
                    )
                } else {
                    AnswersList(
                        briefInfo = answerUiState.briefInfo,
                        openAnswer = openAnswer,
                        deleteAnswer = deleteAnswer,
                    )
                }
            }
        }
    }
}

@Composable
fun EmptyAnswersList(
    getAnswer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 48.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(R.string.answers_list_list_is_empty_title),
        )

        TbocOutlinedButton(
            text = stringResource(R.string.answers_list_get_answer_action),
            onClick = getAnswer,
            modifier = Modifier.padding(top = 32.dp),
        )
    }
}

@Composable
fun AnswersList(
    briefInfo: List<AnswerUi>,
    openAnswer: (Long) -> Unit,
    deleteAnswer: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
    ) {
        items(
            items = briefInfo,
            key = { it.answer.id },
        ) {
            AnswerCard(
                answerUi = it,
                openAnswer = { openAnswer(it.answer.id) },
                deleteAnswer = { deleteAnswer(it.answer.id) },
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp),
            )
        }
    }
}

@Composable
fun AnswerCard(
    answerUi: AnswerUi,
    openAnswer: () -> Unit,
    deleteAnswer: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var menuOffset by remember { mutableStateOf<Offset?>(null) }

    // TODO: use focused surface color while dropdown menu is on the screen

    TbocOutlinedCard(
        onTap = { openAnswer() },
        onLongPress = { menuOffset = it },
        modifier = modifier,
    ) {
        DropdownMenu(
            expanded = (menuOffset != null),
            onDismissRequest = { menuOffset = null },
            // TODO: implement correct offset
            // offset = menuOffset ?: Offset.Zero,
        ) {
            DropdownMenuItem(
                onClick = {
                    menuOffset = null
                    deleteAnswer()
                },
                text = { Text(text = stringResource(R.string.answers_list_delete_answer_menu_item)) }
            )
        }

        Text(
            text = answerUi.answer.question,
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, top = 8.dp)
        ) {

            // TODO: add nice position and layout for hexagram symbol

            TbocHexagramSymbol(
                rawStrokes = answerUi.hexagram.symbolStrokes,
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp)
                    .size(32.dp),
            )

            Text(
                text = buildAnnotatedString {
                    withStyle(style = ParagraphStyle()) {
                        withStyle(SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(answerUi.hexagram.title)
                        }
                    }
                    withStyle(style = ParagraphStyle()) {
                        append(answerUi.hexagram.summary)
                    }
                }
            )
        }

        Text(
            text = answerUi.askedOnStr,
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.74f),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp, end = 16.dp)
                .align(alignment = Alignment.End)
        )
    }
}

@DevicePreviews
@Composable
fun AnswerCardPreview() {
    val hexagram = previewHexagrams[0]

    val answerUi = AnswerUi(
        answer = Answer(
            id = 0,
            askedOn = Clock.System.now(),
            utcOffset = 0,
            question = "This is demo question. How it would be to be a ...?",
            hexagramId = hexagram.id,
        ),
        hexagram = hexagram,
        askedOnStr = "Nov 20, 2020, 08:32"
    )

    TbocTheme {
        Surface(
            modifier = Modifier
            // .fillMaxSize(),
        ) {
            AnswerCard(
                answerUi = answerUi,
                openAnswer = {},
                deleteAnswer = {},
                modifier = Modifier.padding(all = 8.dp)
            )
        }
    }
}