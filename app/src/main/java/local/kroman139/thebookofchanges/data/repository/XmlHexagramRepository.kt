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

import android.content.Context
import android.content.res.XmlResourceParser
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import local.kroman139.thebookofchanges.R
import local.kroman139.thebookofchanges.model.data.Hexagram
import javax.inject.Inject

class XmlHexagramRepository @Inject constructor(
    @ApplicationContext appContext: Context,
) : HexagramRepository {
    private val hexagramList: List<Hexagram>

    init {
        hexagramList = emptyList<Hexagram>()

        val parser = appContext.resources.getXml(R.xml.hexagram_list)

        while(parser.eventType != XmlResourceParser.END_DOCUMENT) {
            println("zzz ${parser.eventType}")
            parser.next()
        }
    }

    override fun getHexagramsStream(): Flow<List<Hexagram>> = flow { emit(hexagramList) }
}