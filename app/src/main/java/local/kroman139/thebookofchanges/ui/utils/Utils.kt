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

package local.kroman139.thebookofchanges.ui.utils

import android.os.Build
import android.util.Log
import kotlinx.datetime.Instant
import kotlinx.datetime.UtcOffset
import kotlinx.datetime.toJavaInstant
import kotlinx.datetime.toJavaZoneOffset
import local.kroman139.thebookofchanges.model.data.Hexagram
import java.time.format.DateTimeFormatter

sealed interface HexagramUiState {
    data class Ok(
        val hexagram: Hexagram,
        val rawStrokes: List<Boolean>,
    ) : HexagramUiState

    object Empty : HexagramUiState
}

fun Hexagram.toUiStateOk(): HexagramUiState.Ok =
    HexagramUiState.Ok(
        hexagram = this,
        rawStrokes = strokes.map { it.solidLine },
    )

fun formatDateTime(
    dateTime: Instant,
    utcSecondsOffset: Int,
): String =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        try {
            DateTimeFormatter
                .ofPattern("MMM d, yyyy, HH:mm")
                .withZone(UtcOffset(seconds = utcSecondsOffset).toJavaZoneOffset())
                .format(dateTime.toJavaInstant())
        } catch (e: Throwable) {
            // TODO: fix it
            Log.e("wrong-date-format", "wrong", e)
            dateTime.toString()
        }
    } else {
        // TODO: fix it
        dateTime.toString()
    }