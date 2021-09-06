package by.sergei.charactercatalog.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import by.sergei.charactercatalog.CharacterRepository
import by.sergei.charactercatalog.model.Character
import by.sergei.charactercatalog.model.ViewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CharactersListViewModel(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val mutableState = MutableStateFlow<ViewState<List<Character>, Throwable>>(ViewState.Loading)
    val state: StateFlow<ViewState<List<Character>, Throwable>> get() = mutableState

    init {
        viewModelScope.launch {
            mutableState.value = ViewState.Result(characterRepository.loadCharacters())
        }
    }
}