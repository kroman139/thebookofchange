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

package local.kroman139.thebookofchanges.designsystem.component

import androidx.compose.foundation.layout.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.lifecycle.Lifecycle

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TbocOutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    label: @Composable (() -> Unit)? = null,
    maxLines: Int = Int.MAX_VALUE,
    maxLength: Int = Int.MAX_VALUE,
) {
    val focusManager = LocalFocusManager.current

    val keyboardState by keyboardAsState()
    // TODO: This default value may lead to defects.
    var keyboardPrevState by remember { mutableStateOf(Keyboard.Closed) }

    SideEffect {
        if (keyboardPrevState != keyboardState) {
            if (keyboardState == Keyboard.Closed) {
                focusManager.clearFocus()
            }
            keyboardPrevState = keyboardState
        }
    }

    Column(
        modifier = modifier
    ) {

        OutlinedTextField(
            value = value,
            label = label,
            onValueChange = {
                if (it.text.length > maxLength) {
                    // Ignore text input if it's too big.
                    // TODO: this behavior may be wrong if user tries to paste a line of text.
                    // We shall not ignore the whole paste-line without feedback to user.
                    // E.g., we may insert part of paste-line instead.
                } else {
                    onValueChange(it)
                }
            },
            modifier = Modifier
                .fillMaxWidth(),
            maxLines = maxLines,
        )

        // TODO: we must use BasicTextField(decorationBox = ..) instead of this Text().
        Text(
            text = "${value.text.length} / $maxLength",
            textAlign = TextAlign.End,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}

// See https://stackoverflow.com/a/69533584 for more info
enum class Keyboard {
    Opened, Closed
}

/** if you want, you can use boolean instead to return an enum. */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun keyboardAsState(): State<Keyboard> {
    val lifecycle = LocalLifecycleOwner.current.lifecycle
    val isResumed = lifecycle.currentState == Lifecycle.State.RESUMED
    return rememberUpdatedState(if (WindowInsets.isImeVisible && isResumed) Keyboard.Opened else Keyboard.Closed)
}