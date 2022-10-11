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

        val ns: String? = null
        val parser = appContext.resources.getXml(R.xml.hexagram_list)
        var eventType = -1

        println("zzz 0 parser.name = ${parser.name}, eventType = $eventType")

        // parser.require(XmlResourceParser.START_DOCUMENT, ns, "hexagram_list")
        eventType = parser.next()
        println("zzz 1 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")

        println("zzz parser.eventType = ${parser.eventType}")
        parser.require(XmlResourceParser.START_DOCUMENT, ns, null)
        eventType = parser.next()

        // zzz 1.1 parser.name = hexagram_list, eventType = 2 (START_TAG)
        println("zzz 1.1 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")

        parser.require(XmlResourceParser.START_TAG, ns, "hexagram_list")
        eventType = parser.next()
        println("zzz 2 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")

        //parser.require(XmlResourceParser.START_TAG, ns, "hexagram")
        eventType = parser.next()
        println("zzz 3 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")


        while(false && eventType != XmlResourceParser.END_DOCUMENT) {

            if (eventType == XmlResourceParser.START_DOCUMENT) {

                println("zzz parser.name = ${parser.name}, eventType = $eventType")
                eventType = parser.next()
                println("zzz parser.name = ${parser.name}, eventType = $eventType")
                println("zzz after nextTag()")
                parser.require(XmlResourceParser.START_TAG, ns, "hexagram_list")

                eventType = parser.next()
                println("zzz parser.name = ${parser.name}, eventType = $eventType")

                parser.nextTag()
                parser.require(XmlResourceParser.START_TAG, ns, "hexagram")
                eventType = parser.next()
                println("zzz parser.name = ${parser.name}, eventType = $eventType")

            }
            if ((eventType == XmlResourceParser.START_TAG) && (parser.name == "hexagram_list")) {
                //parser.nextTag()
                parser.require(XmlResourceParser.START_TAG, ns, "hexagram")
                val z = parser.next()
                println("zzz parser.name = ${parser.name}, z = $z")

            }
            eventType = parser.next()
            println("zzz parser.next() == $eventType")
        }
    }

    override fun getHexagramsStream(): Flow<List<Hexagram>> = flow { emit(hexagramList) }
}