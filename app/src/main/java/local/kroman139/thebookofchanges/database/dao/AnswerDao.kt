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

package local.kroman139.thebookofchanges.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import local.kroman139.thebookofchanges.database.model.AnswerEntity

@Dao
interface AnswerDao {
    @Query(
        value = """
        SELECT * FROM answer
        WHERE id = :answerId
    """
    )
    fun getAnswerEntityStream(answerId: Long): Flow<AnswerEntity>

    @Query(value = "SELECT * FROM answer")
    fun getAllAnswerEntitiesStream(): Flow<List<AnswerEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertOrIgnoreAnswer(answerEntity: AnswerEntity): Long

    @Query(
        value = """
            DELETE FROM answer
            WHERE id = :id
        """
    )
    suspend fun deleteAnswer(id: Long)
}