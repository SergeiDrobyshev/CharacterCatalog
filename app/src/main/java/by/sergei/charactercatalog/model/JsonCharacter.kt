package by.sergei.charactercatalog.model

import kotlinx.serialization.Serializable

@Serializable
class JsonCharacter(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    //val origin: String,
    //val location: String,
    val image: String,
    //val episode: List<String>,
    val url: String,
    val created: String
)

