package by.sergei.charactercatalog.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.sergei.charactercatalog.CharacterRepository
import by.sergei.charactercatalog.model.CharacterDetails
import by.sergei.charactercatalog.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharacterDetailsViewModel(
    private val characterRepository: CharacterRepository,
    val id: Long
): ViewModel() {
    private val mutableState = MutableStateFlow<ViewState<CharacterDetails, Throwable>>(ViewState.Loading)
    val state: StateFlow<ViewState<CharacterDetails, Throwable>> get() = mutableState

    init {
        viewModelScope.launch {
            mutableState.value = ViewState.Result(characterRepository.loadCharacterById(id))
        }
    }
}