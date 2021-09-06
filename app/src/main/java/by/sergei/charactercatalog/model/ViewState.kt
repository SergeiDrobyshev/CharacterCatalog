package by.sergei.charactercatalog.model

sealed class ViewState<out S, out E> {

    object Loading : ViewState<Nothing, Nothing>()

    data class Result<out S, out E>(val result: ValueResult<S, E>) : ViewState<S, E>()

}