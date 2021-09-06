package by.sergei.charactercatalog.model

sealed class ValueResult<out S, out E> {

    data class Success<out S>(val value: S): ValueResult<S, Nothing>()

    data class Error<out E>(val value: E): ValueResult<Nothing, E>()
}