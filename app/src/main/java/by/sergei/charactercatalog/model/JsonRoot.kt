package by.sergei.charactercatalog.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class JsonRoot (
    @SerialName("results")
    val characters: List<JsonCharacter>
)