package by.sergei.charactercatalog

import by.sergei.charactercatalog.retrofit.RetrofitServices
import by.sergei.charactercatalog.model.Character
import by.sergei.charactercatalog.model.CharacterDetails
import by.sergei.charactercatalog.model.JsonCharacter
import by.sergei.charactercatalog.model.ValueResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface CharacterRepository {
    suspend fun loadCharacters(): ValueResult<List<Character>, Throwable>
    suspend fun loadCharacterById(id: Long): ValueResult<CharacterDetails, Throwable>
}

class JsonCharacterRepository(private val retrofitService: RetrofitServices) : CharacterRepository {

    override suspend fun loadCharacters(): ValueResult<List<Character>, Throwable> =
        withContext(Dispatchers.IO) {
            try {
                val value = parseCharacters(retrofitService.getAllCharacters().characters)
                ValueResult.Success(value)
            } catch (error: Throwable) {
                ValueResult.Error(error)
            }
        }

    override suspend fun loadCharacterById(id: Long): ValueResult<CharacterDetails, Throwable> =
        withContext(Dispatchers.IO) {
            try {
                val value = parseSingleCharacter(retrofitService.getCharacterById(id))
                ValueResult.Success(value)
            } catch (error: Throwable) {
                ValueResult.Error(error)
            }
        }

    private fun parseCharacters(data: List<JsonCharacter>): List<Character> {
        return data.map { jsonCharacter ->
            Character(
                id = jsonCharacter.id,
                name = jsonCharacter.name,
                imageUrl = jsonCharacter.image
            )
        }
    }

    private fun parseSingleCharacter(character: JsonCharacter): CharacterDetails {
        return CharacterDetails(
            id = character.id,
            name = character.name,
            status = character.status,
            species = character.species,
            type = character.type,
            gender = character.gender,
            image = character.image,
            url = character.url,
            created = character.created
        )
    }
}