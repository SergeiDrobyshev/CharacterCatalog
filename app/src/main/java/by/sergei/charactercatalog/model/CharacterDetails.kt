package by.sergei.charactercatalog.model

data class CharacterDetails(
    val id: Long,
    val name: String,
    val status: String,
    val species: String,
    val type: String,
    val gender: String,
    //val origin: Origin,
    //val location: Location,
    val image: String,
    //val episode: Episode,
    val url: String,
    val created: String
)