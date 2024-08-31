package jp.ac.it_college.std.s23019.apisample1

import com.google.gson.Gson
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

data class AnimeResponse(val data: AnimeData?)
data class AnimeData(val title: String?, val synopsis: String?)


class AnimeApiClient {
    private val client = OkHttpClient()
    private val gson = Gson()

    fun getRandomAnime(): AnimeData? {
        val request = Request.Builder()
            .url("https://api.jikan.moe/v4/random/anime")
            .build()

        return try {
            client.newCall(request).execute().use { response ->
                if (!response.isSuccessful) throw IOException("Unexpected code $response")

                val responseBody = response.body?.string() ?: return null
                println("Response Body: $responseBody") // ログ出力

                val animeResponse = gson.fromJson(responseBody, AnimeResponse::class.java)
                val animeData = animeResponse.data ?: return null

                val title = animeData.title ?: "Unknown Title"
                val synopsis = animeData.synopsis ?: "No Synopsis"
                AnimeData(title = title, synopsis = synopsis)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }
}
