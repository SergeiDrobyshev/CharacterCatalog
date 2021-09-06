package by.sergei.charactercatalog.retrofit

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object Retrofit {
    private const val BASE_URL = "https://rickandmortyapi.com"
    fun build(): Retrofit {
        val jsonFormat = Json{ ignoreUnknownKeys = true }
       return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(jsonFormat.asConverterFactory("application/json".toMediaType()))
                .build()
    }
}