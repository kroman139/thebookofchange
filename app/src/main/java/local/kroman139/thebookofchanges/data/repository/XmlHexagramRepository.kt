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
import local.kroman139.thebookofchanges.model.data.HexagramStroke
import javax.inject.Inject

class XmlHexagramRepository @Inject constructor(
    @ApplicationContext appContext: Context,
) : HexagramRepository {
    private val hexagramList: List<Hexagram>

    init {
        val tempHexaList = mutableListOf<Hexagram>()

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

        // zzz 1.1 parser.name = hexagram_list, eventType = 2, parser.eventType = 2
        println("zzz 1.1 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")

        parser.require(XmlResourceParser.START_TAG, ns, "hexagram_list")
        eventType = parser.next()

        // zzz 2 parser.name = hexagram, eventType = 2, parser.eventType = 2
        println("zzz 2 parser.name = ${parser.name}, eventType = $eventType, parser.eventType = ${parser.eventType}")
        while (parser.name == "hexagram" && parser.eventType == XmlResourceParser.START_TAG) {
            val hexa = parser.readHexagramNode()
            tempHexaList += hexa
            println("zzz 3 hexa = $hexa")
        }

        parser.close()

        hexagramList = tempHexaList.toList()
    }

    private fun XmlResourceParser.readHexagramNode(): Hexagram {
        var z = ""
        val map = mutableMapOf<String, Any>()

        // Read attributes (4 items)
        (0..3).forEach {
            map[getAttributeName(it)] = getAttributeValue(it)
        }

        // skip "hexagram, START"
        next()

        while (eventType == XmlResourceParser.START_TAG) {
            map[name] = readHexagramElement()
        }

        // skip "hexagram, END"
        require(name == "hexagram" && eventType == XmlResourceParser.END_TAG)
        next()

//        println("zzz readHexagramNode: map = $map")
        return Hexagram(
            id = map["id"] as? String ?: throw Throwable("Attribute 'id' is not found"),
            symbol = map["symbol"] as? String ?: throw Throwable("Attribute 'symbol' is not found"),
            logogram = map["logogram"] as? String ?: throw Throwable("Attribute 'logogram' is not found"),
            title = map["title"] as? String ?: throw Throwable("Attribute 'title' is not found"),

            text = map["text"] as? String ?: throw Throwable("Node 'text' is not found"),
            summary = map["summary"] as? String ?: throw Throwable("Node 'summary' is not found"),

            strokes = map["strokes"] as? List<HexagramStroke> ?: throw Throwable("Node 'strokes' is not found"),
        )
    }

    private fun XmlResourceParser.readHexagramElement(): Any {
        return when (name) {
            "text" -> readText()
            "summary" -> readText()
            "strokes" -> readStrokesNode()
            else -> throw Throwable("error")
        }
    }

    private fun XmlResourceParser.readStrokesNode(): List<HexagramStroke> {
        val strokes = mutableListOf<HexagramStroke>()

        // skip "strokes, START"
        next()

        while (name == "stroke" && eventType == XmlResourceParser.START_TAG) {
            val miniMap = mutableMapOf<String, String>()

            // read attribute "solid"
            miniMap[getAttributeName(0)] = getAttributeValue(0)

            // skip "stroke, START"
            next()

            // read "text" & "summary"
            miniMap[name] = readText()
            miniMap[name] = readText()

            // skip "stroke, END"
            next()

            // Build hexa-stroke
            strokes.add(
                HexagramStroke(
                    solidLine = when (miniMap["solid"]) {
                        "true" -> true
                        "false" -> false
                        else -> throw Throwable("Unknown value '${miniMap["solid"]}' for solid-attribute")
                    },
                    text = miniMap["text"] ?: throw Throwable("Node 'text' is not found"),
                    summary = miniMap["summary"] ?: throw Throwable("Node 'summary' is not found"),
                )
            )
        }

        // skip "strokes, END"
        next()

        return strokes.toList()
    }

    private fun XmlResourceParser.readText() = nextText().also { next() }

    override fun getHexagramsStream(): Flow<List<Hexagram>> = flow { emit(hexagramList) }
}