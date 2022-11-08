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

package local.kroman139.thebookofchanges.ui.getanswer

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.TbocOutlinedButton
import local.kroman139.thebookofchanges.designsystem.component.TbocOutlinedTextField
import local.kroman139.thebookofchanges.designsystem.component.TbocScreen
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme

@Composable
fun GetAnswerRoute(
    navigateBack: () -> Unit,
    openAnswer: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GetAnswerViewModel = hiltViewModel(),
) {
    GetAnswerScreen(
        navigateBack = navigateBack,
        autoGetAnswer = { question ->
            viewModel.autoGetAndOpenAnswer(
                question = question,
                navigateToAnswer = openAnswer,
            )
        },
        modifier = modifier,
    )
}

@Composable
fun GetAnswerScreen(
    navigateBack: () -> Unit,
    autoGetAnswer: (question: String) -> Unit,
    modifier: Modifier = Modifier,
) {
    TbocScreen(
        navigateBack = navigateBack,
        titleText = stringResource(R.string.get_answer_screen_title),
        modifier = modifier,
    ) {

        // TODO: add scrolling support

        // TODO: add custom answer with 3 coins or yarrow stalks


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp)
                .imePadding(),
        ) {
            val maxLen = 400

            var textState by rememberSaveable(stateSaver = TextFieldValue.Saver) {
                mutableStateOf(TextFieldValue())
            }

            TbocOutlinedTextField(
                maxLength = maxLen,
                value = textState,
                onValueChange = { textState = it },
                label = { Text(text = stringResource(R.string.get_answer_question_title)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 32.dp),
            )

            TbocOutlinedButton(
                text = stringResource(R.string.get_answer_auto_button_title),
                modifier = Modifier
                    .align(alignment = CenterHorizontally)
                    .padding(top = 32.dp),
                onClick = { autoGetAnswer(textState.text.trim().ifEmpty { "..." }) },
            )
        }
    }
}

@DevicePreviews
@Composable
fun GetAnswerScreenPreview() {
    TbocTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            GetAnswerScreen(
                navigateBack = { },
                autoGetAnswer = { },
            )
        }
    }
}