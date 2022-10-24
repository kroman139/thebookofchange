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

package local.kroman139.thebookofchanges.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import local.kroman139.thebookofchanges.ui.aboutbook.AboutBookRoute
import local.kroman139.thebookofchanges.ui.aboutbook.navigation.AboutBookDestination
import local.kroman139.thebookofchanges.ui.answer.AnswerRoute
import local.kroman139.thebookofchanges.ui.answer.navigation.AnswerDestination
import local.kroman139.thebookofchanges.ui.answerslist.AnswersListRoute
import local.kroman139.thebookofchanges.ui.answerslist.navigation.AnswersListDestination
import local.kroman139.thebookofchanges.ui.askquestion.AskQuestionRoute
import local.kroman139.thebookofchanges.ui.askquestion.navigation.AskQuestionDestination
import local.kroman139.thebookofchanges.ui.hexagram.HexagramRoute
import local.kroman139.thebookofchanges.ui.hexagram.navigation.HexagramDestination
import local.kroman139.thebookofchanges.ui.hexalibrary.HexaLibraryRoute
import local.kroman139.thebookofchanges.ui.hexalibrary.navigation.HexaLibraryDestination
import local.kroman139.thebookofchanges.ui.home.HomeRoute
import local.kroman139.thebookofchanges.ui.home.navigation.HomeDestination

@Composable
fun TbocNavHost(
    navController: NavHostController,
    navigate: (TbocNavigationDestination, String) -> Unit,
    navigateFromHome: (TbocNavigationDestination, String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    startDestination: String = HomeDestination.route,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        composable(route = HomeDestination.route) {
            HomeRoute(
                openLibrary = { navigate(HexaLibraryDestination, HexaLibraryDestination.route) },
                askQuestion = { navigate(AskQuestionDestination, AskQuestionDestination.route) },
                showAnswers = { navigate(AnswersListDestination, AnswersListDestination.route) },
                aboutBook = { navigate(AboutBookDestination, AboutBookDestination.route) },
            )
        }
        composable(route = HexaLibraryDestination.route) {
            HexaLibraryRoute(
                onBackClick = onBackClick,
                navigateToHexagram = {
                    navigate(HexagramDestination, HexagramDestination.createNavigationRoute(it))
                }
            )
        }
        composable(route = AskQuestionDestination.route) {
            AskQuestionRoute(
                onBackClick = onBackClick,
                openAnswer = {
                    navigateFromHome(AnswerDestination, AnswerDestination.createNavigationRoute(it))
                }
            )
        }
        composable(route = AnswersListDestination.route) {
            AnswersListRoute(
                onBackClick = onBackClick,
                openAnswer = {
                    navigate(AnswerDestination, AnswerDestination.createNavigationRoute(it))
                },
            )
        }
        composable(
            route = AnswerDestination.route,
            arguments = listOf(
                AnswerDestination.questionIdNavArgument()
            )
        ) {
            AnswerRoute(
                onBackClick = onBackClick
            )
        }
        composable(
            route = HexagramDestination.route,
            arguments = listOf(
                HexagramDestination.hexagramIdNavArgument()
            )
        ) {
            HexagramRoute(
                onBackClick = onBackClick
            )
        }
        composable(route = AboutBookDestination.route) {
            AboutBookRoute(
                onBackClick = onBackClick
            )
        }
    }
}