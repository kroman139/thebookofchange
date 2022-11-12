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

package local.kroman139.thebookofchanges.ui.answer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.datetime.Clock
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.TbocHexagramView
import local.kroman139.thebookofchanges.designsystem.component.TbocScreen
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.Answer
import local.kroman139.thebookofchanges.model.data.previewHexagrams

@Composable
fun AnswerRoute(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnswerViewModel = hiltViewModel(),
) {
    val answerUiState by viewModel.answerUiStream.collectAsState()

    AnswerScreen(
        answerUi = answerUiState,
        navigateBack = navigateBack,
        modifier = modifier,
    )
}

@Composable
fun AnswerScreen(
    answerUi: AnswerUiState,
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when (answerUi) {
        is AnswerUiState.Loading ->
            TbocScreen(
                navigateBack = navigateBack,
                modifier = modifier,
                titleText = "Loading answer...",
            ) {
            }
        is AnswerUiState.Success ->
            TbocScreen(
                navigateBack = navigateBack,
                modifier = modifier,
                titleText = stringResource(R.string.answer_screen_title),
            ) {
                QuestionWithAnswer(answerUi)
            }
    }
}

@Composable
fun QuestionWithAnswer(
    answerUiSuccess: AnswerUiState.Success,
    modifier: Modifier = Modifier,
) {
    val askedOnStr = answerUiSuccess.askedOnStr
    val answer = answerUiSuccess.answer
    val hexagram = answerUiSuccess.hexagram

    val adoptedQuestion = answer.question.ifBlank { "..." }

    Column(
        modifier = modifier
            .verticalScroll(rememberScrollState())
            .padding(start = 16.dp, end = 16.dp),
    ) {
        Text(
            text = "${adoptedQuestion}",
            modifier = Modifier.align(alignment = Alignment.End),
        )

        Text(
            text = stringResource(R.string.answer_screen_answered_on)+ "\n$askedOnStr",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.74f),
            modifier = Modifier
                .padding(top = 8.dp, bottom = 16.dp)
                .align(alignment = Alignment.End)
        )

        // Divider(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp))

        TbocHexagramView(
            hexagram = hexagram,
        )
    }
}

@DevicePreviews
@Composable
fun QuestionWithAnswerPreview() {
    val hexagram = previewHexagrams[0]

    val answer = AnswerUiState.Success(
        askedOnStr = "Oct 21, 2022, 20:20 pm",
        answer = Answer(
            id = 0,
            askedOn = Clock.System.now(),
            utcOffset = 0,
            question = "This is demo question. How it would be to be a ...?",
            hexagramId = hexagram.id,
        ),
        hexagram = hexagram,
    )

    TbocTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            AnswerScreen(
                answerUi = answer,
                navigateBack = {},
            )
        }
    }
}