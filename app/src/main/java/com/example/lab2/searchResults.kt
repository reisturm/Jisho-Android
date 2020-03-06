package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_search_results.*
import okhttp3.*
import java.io.IOException

class searchResults : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_results)

        recyclerView_main.layoutManager = LinearLayoutManager(this)

        val search_term = intent.getStringExtra("search_query")
        fetchJson(search_term)
    }
    fun fetchJson(word: String) {
        val api = "https://jisho.org/api/v1/search/words?keyword="
        val url = api + word

        val request = Request.Builder().url(url).build()

        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()

                val gson = GsonBuilder().create()
                val kanjiResults = gson.fromJson(body, KanjiResults::class.java)

                runOnUiThread {
                    recyclerView_main.adapter = MainAdapter(kanjiResults)
                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }
}

class KanjiResults(val meta: Meta, val data: List<dObjects>)

class Meta(val status: Int)

class dObjects(val japanese: List<jObjects>, val senses: List<sObjects>)

class jObjects(val word: String, val reading: String)

class sObjects(val english_definitions: List<String>)

