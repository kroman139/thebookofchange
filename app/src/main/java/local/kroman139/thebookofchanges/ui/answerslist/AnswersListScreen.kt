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

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DummyText
import local.kroman139.thebookofchanges.designsystem.component.TbocBackButton
import local.kroman139.thebookofchanges.designsystem.component.TbocDummyButton
import local.kroman139.thebookofchanges.ui.hexalibrary.HexaLibraryScreen
import local.kroman139.thebookofchanges.ui.hexalibrary.HexaLibraryViewModel

@Composable
fun AnswersListRoute(
    onBackClick: () -> Unit,
    openAnswer: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnswersListViewModel = hiltViewModel(),
) {

    AnswersListScreen(
        uiState = "23",
        onBackClick = onBackClick,
        openAnswer = openAnswer,
        modifier = modifier,
    )
}

@Composable
fun AnswersListScreen(
    uiState: String,
    onBackClick: () -> Unit,
    openAnswer: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.background(color = Color.Gray),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {
            Row {
                TbocBackButton(
                    onClick = onBackClick,
                )

                DummyText(
                    text = "Question/Answer",
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.padding(start = 16.dp),
                )
            }

            Column {
                DummyText(
                    text = "your question/answer list"
                )

                TbocDummyButton(
                    text = "open answer $uiState",
                    onClick = { openAnswer(uiState) }
                )
            }
        }
    }
}