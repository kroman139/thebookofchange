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

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import local.kroman139.thebookofchanges.data.repository.AnswerRepository
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class GetAnswerViewModel @Inject constructor(
    private val answerRepository: AnswerRepository,
) : ViewModel() {

    private val rnd = Random(System.currentTimeMillis())

    fun autoGetAndOpenAnswer(
        question: String,
        navigateToAnswer: (Long) -> Unit,
    ) {
        viewModelScope.launch {
            val hexagramId = (1..64).random(rnd).toString()

            println("scope launch: $question, $hexagramId")

            val res = answerRepository.insertAnswer(
                question = question,
                hexagramId = hexagramId
            )

            println("scope result: $res")

            navigateToAnswer(res)
        }
    }
}