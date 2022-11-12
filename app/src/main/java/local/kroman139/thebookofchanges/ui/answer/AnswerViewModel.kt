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

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import local.kroman139.thebookofchanges.data.repository.AnswerRepository
import local.kroman139.thebookofchanges.data.repository.HexagramRepository
import local.kroman139.thebookofchanges.model.data.Answer
import local.kroman139.thebookofchanges.model.data.Hexagram
import local.kroman139.thebookofchanges.ui.answer.navigation.AnswerDestination
import local.kroman139.thebookofchanges.ui.utils.formatDateTime
import javax.inject.Inject

@HiltViewModel
class AnswerViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    hexagramRepository: HexagramRepository,
    answerRepository: AnswerRepository,
) : ViewModel() {
    private val answerId: Long =
        checkNotNull(savedStateHandle[AnswerDestination.answerIdArg])

    val answerUiStream: StateFlow<AnswerUiState> =
        combine(
            answerRepository.getAnswer(answerId),
            hexagramRepository.getHexagramsStream()
        ) { answer, hexagramList ->

            AnswerUiState.Success(
                askedOnStr = formatDateTime(answer.askedOn, answer.utcOffset / 1000),
                answer = answer,
                hexagram = hexagramList.first { it.id == answer.hexagramId }
            )
        }

            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = AnswerUiState.Loading
            )
}

sealed interface AnswerUiState {
    data class Success(
        val askedOnStr: String,
        val answer: Answer,
        val hexagram: Hexagram,
    ) : AnswerUiState

    object Loading : AnswerUiState
}
