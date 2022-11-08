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

package local.kroman139.thebookofchanges.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.Clock
import local.kroman139.thebookofchanges.database.dao.AnswerDao
import local.kroman139.thebookofchanges.database.model.AnswerEntity
import local.kroman139.thebookofchanges.database.model.asExternalModel
import local.kroman139.thebookofchanges.model.data.Answer
import java.util.*
import javax.inject.Inject

class DatabaseAnswerRepository @Inject constructor(
    private val answerDao: AnswerDao,
) : AnswerRepository {

    override fun getAnswer(id: Long): Flow<Answer> =
        answerDao
            .getAnswerEntityStream(id)
            .map {
                it.asExternalModel()
            }

    override fun getAllAnswers(): Flow<List<Answer>> =
        answerDao
            .getAllAnswerEntitiesStream()
            .map { it.map(AnswerEntity::asExternalModel) }

    override suspend fun insertAnswer(
        question: String,
        hexagramId: String,
    ) =
        answerDao.insertOrIgnoreAnswer(
            answerEntity = AnswerEntity(
                id = 0,
                askedOn = Clock.System.now(),
                utcOffset = TimeZone.getDefault().getOffset(System.currentTimeMillis()),
                question = question,
                hexagramId = hexagramId,
            )
        )

    override suspend fun deleteAnswer(id: Long) =
        answerDao.deleteAnswer(id)
}