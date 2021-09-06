package by.sergei.charactercatalog.retrofit

import by.sergei.charactercatalog.model.JsonCharacter
import by.sergei.charactercatalog.model.JsonRoot
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitServices {
    @GET("/api/character")
    suspend fun getAllCharacters(): JsonRoot

    @GET("/api/character/{id}")
    suspend fun getCharacterById(@Path("id")id: Long): JsonCharacter
}