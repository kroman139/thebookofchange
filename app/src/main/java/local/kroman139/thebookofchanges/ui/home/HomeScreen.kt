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

package local.kroman139.thebookofchanges.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.designsystem.component.DevicePreviews
import local.kroman139.thebookofchanges.designsystem.component.TbocDummyButton
import local.kroman139.thebookofchanges.designsystem.component.TbocOutlinedButton
import local.kroman139.thebookofchanges.designsystem.theme.TbocTheme
import local.kroman139.thebookofchanges.model.data.previewHexagrams
import local.kroman139.thebookofchanges.ui.utils.HexagramUiState
import local.kroman139.thebookofchanges.ui.utils.toUiStateOk

@Composable
fun HomeRoute(
    openLibrary: () -> Unit,
    getAnswer: () -> Unit,
    showAnswers: () -> Unit,
    aboutBook: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val hexagramListUiState by viewModel.hexagramListUiState.collectAsState()

    HomeScreen(
        hexagramListUiStateOk = hexagramListUiState,
        openLibrary = openLibrary,
        getAnswer = getAnswer,
        showAnswers = showAnswers,
        aboutBook = aboutBook,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    hexagramListUiStateOk: List<HexagramUiState.Ok>,
    openLibrary: () -> Unit,
    getAnswer: () -> Unit,
    showAnswers: () -> Unit,
    aboutBook: () -> Unit,
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
        TbocDummyButton(
            text = "library",
            onClick = openLibrary,
        )

        TbocOutlinedButton(
            text = stringResource(R.string.home_ask_question_action),
            onClick = getAnswer,
            modifier = Modifier.fillMaxWidth()
        )

        TbocOutlinedButton(
            text = stringResource(R.string.home_open_answers_action),
            onClick = showAnswers,
            modifier = Modifier.fillMaxWidth()
        )

        TbocDummyButton(
            text = "about book",
            onClick = aboutBook,
        )
    }
}

@DevicePreviews
@Composable
fun HomeScreenPreview() {
    TbocTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
        ) {
            HomeScreen(
                hexagramListUiStateOk = previewHexagrams.map { it.toUiStateOk() },
                openLibrary = { },
                getAnswer = { },
                showAnswers = { },
                aboutBook = { },
            )
        }
    }
}