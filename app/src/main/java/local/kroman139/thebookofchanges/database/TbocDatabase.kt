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

package local.kroman139.thebookofchanges.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import local.kroman139.thebookofchanges.database.dao.AnswerDao
import local.kroman139.thebookofchanges.database.model.AnswerEntity
import local.kroman139.thebookofchanges.database.util.InstantConverter

@Database(
    entities = [
        AnswerEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(
    InstantConverter::class,
)
abstract class TbocDatabase : RoomDatabase() {
    abstract fun answerDao(): AnswerDao
}