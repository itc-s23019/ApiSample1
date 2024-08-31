package jp.ac.it_college.std.s23019.apisample1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import jp.ac.it_college.std.s23019.apisample1.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {

    private val animeApiClient = AnimeApiClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.fetchButton.setOnClickListener {
            fetchRandomAnime(binding)
        }
    }

    private fun fetchRandomAnime(binding: ActivityMainBinding) {
        thread {
            val anime = animeApiClient.getRandomAnime()
            anime?.let {
                runOnUiThread {
                    binding.animeTitle.text = it.title
                    binding.animeSynopsis.text = it.synopsis
                }
            }
        }
    }
}
