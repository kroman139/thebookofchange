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
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import local.kroman139.thebookofchanges.designsystem.component.DummyText
import local.kroman139.thebookofchanges.designsystem.component.TbocDummyButton
import local.kroman139.thebookofchanges.designsystem.component.TbocScreen
import local.kroman139.thebookofchanges.model.data.Answer

@Composable
fun AnswersListRoute(
    navigateBack: () -> Unit,
    openAnswer: (Long) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnswersListViewModel = hiltViewModel(),
) {

    val x by viewModel.dummyAns.collectAsState()

    AnswersListScreen(
        uiState = x,
        navigateBack = navigateBack,
        openAnswer = openAnswer,
        modifier = modifier,
    )
}

@Composable
fun AnswersListScreen(
    uiState: List<Answer>,
    navigateBack: () -> Unit,
    openAnswer: (Long) -> Unit,
    modifier: Modifier = Modifier,
) {
    SideEffect {
        println("AnswersListScreen: uiState = $uiState")
    }

    TbocScreen(
        navigateBack = navigateBack,
        titleText = "My answers",
        modifier = modifier,
    ) {
//        LazyColumn(
//            modifier = Modifier
//                .fillMaxWidth()
//                .verticalScroll(rememberScrollState())
//                .padding(all = 16.dp),
//        ) {
//
//            items(
//                items = uiState,
//                key = { it.id },
//            ) {
//                //Card {
//                    //Column {
//                        DummyText(
//                            text = "${it.question}"
//                        )
//
//                        TbocDummyButton(
//                            text = "view ${it.hexagramId}",
//                            onClick = { openAnswer(it.id) }
//                        )
//                    //}
//                //}
//            }
//        }


//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(start = 8.dp, end = 8.dp)
//                //.verticalScroll(rememberScrollState())
//                .imePadding(),
//        ) {
//        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(rememberScrollState())
                .padding(all = 16.dp),
        ) {

//            items(
//                items = uiState,
//                key = { it.id },
//            ) {
//
//                //uiState.forEach {
//                Card(
//                    //modifier = Modifier.fillMaxWidth()
//                ) {
//                    //Column {
//                    DummyText(
//                        text = "${it.question}"
//                    )
//
//                    TbocDummyButton(
//                        text = "view ${it.hexagramId}",
//                        onClick = { openAnswer(it.id) }
//                    )
//                    //}
//                }
//            }
        }
    }
}