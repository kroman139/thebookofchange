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

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import local.kroman139.thebookofchanges.data.repository.AnswerRepository
import local.kroman139.thebookofchanges.data.repository.HexagramRepository
import local.kroman139.thebookofchanges.model.data.Answer
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.ui.utils.formatDateTime
import javax.inject.Inject

@HiltViewModel
class AnswersListViewModel @Inject constructor(
    hexagramRepository: HexagramRepository,
    val answerRepository: AnswerRepository,
) : ViewModel() {

    val answersUiState =
        combine(
            hexagramRepository.getHexagramsStream(),
            answerRepository.getAllAnswers()
        ) { hexagrams, answers ->
            when {
                hexagrams.isEmpty() -> AnswerUiState.Loading
                else -> AnswerUiState.Success(briefInfo(hexagrams, answers))
            }
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AnswerUiState.Loading
            )

    fun deleteAnswer(id: Long) {
        viewModelScope.launch {
            answerRepository.deleteAnswer(id)
        }
    }
}

private fun briefInfo(
    hexagrams: List<Hexagram>,
    answers: List<Answer>
): List<AnswerUi> {

    val hexaMap = hexagrams.associateBy { it.id }

    return answers
        .filter {
            if (!hexaMap.containsKey(it.hexagramId)) {
                Log.e(
                    "AnswersListViewModel",
                    "Unknown hexagram id: answer.hexagramId = ${it.hexagramId}, hexaMap.keys = ${hexaMap.keys}"
                )
            }
            hexaMap.containsKey(it.hexagramId)
        }
        .sortedByDescending { it.askedOn }
        .map {
            AnswerUi(
                answer = it,
                hexagram = hexaMap[it.hexagramId]!!,
                askedOnStr = formatDateTime(it.askedOn, it.utcOffset / 1000),
            )
        }
}

data class AnswerUi(
    val answer: Answer,
    val hexagram: Hexagram,
    val askedOnStr: String
)

sealed interface AnswerUiState {
    data class Success(
        val briefInfo: List<AnswerUi>
    ) : AnswerUiState

    object Loading : AnswerUiState
}