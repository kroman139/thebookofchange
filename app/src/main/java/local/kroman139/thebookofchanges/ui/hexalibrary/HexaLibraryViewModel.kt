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

package local.kroman139.thebookofchanges.ui.hexalibrary

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import local.kroman139.thebookofchanges.data.repository.HexagramRepository
import local.kroman139.thebookofchanges.ui.utils.toUiStateOk
import javax.inject.Inject

// Technique 1: save "viewMode" inside ViewModel.
// Reason: to show "combine" in action.

@HiltViewModel
class HexaLibraryViewModel @Inject constructor(
    hexagramRepository: HexagramRepository,
) : ViewModel() {

    val hexaLibraryUiState =
        hexagramRepository.getHexagramsStream()
            .map { list ->
                HexaLibraryUiState.Library(
                    hexaList = list,
                )
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = HexaLibraryUiState.Empty
            )
}