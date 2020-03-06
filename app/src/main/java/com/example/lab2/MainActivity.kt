package com.example.lab2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.widget.Toolbar


class MainActivity : AppCompatActivity() {

    var aboutFragmentLoaded = false;

    val manager = supportFragmentManager

    val context = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)



        val textbox: EditText = findViewById(R.id.searchQuery)

        val search: Button = findViewById(R.id.searchButton);
        search.setOnClickListener {
            val search_term = textbox.text.toString();
            if (search_term == "") {
                val text = "Please enter a search query."
                Toast.makeText(applicationContext, text, Toast.LENGTH_SHORT).show()
            }
            else {
                if (search_term.length > 0) {
                    val search_query = SearchTerm(search_term)
                }
                val intent = Intent(this, searchResults::class.java)
                intent.putExtra("search_query", search_term)
                startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var selectedOption = ""

        when(item.itemId) {
            R.id.about -> selectedOption = "About"
        }

        if (selectedOption == "About") {
            showAbout()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    fun showAbout() {
        val transaction = manager.beginTransaction()
        val fragment = aboutFragment()

        if (!aboutFragmentLoaded) {
            aboutFragmentLoaded = true
            transaction.replace(R.id.fragment_holder, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        } else {
            aboutFragmentLoaded = false;
            showEmpty()
        }
    }

    fun showEmpty() {
        val transaction = manager.beginTransaction()
        val fragment = emptyFragment()
        transaction.replace(R.id.fragment_holder, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
        aboutFragmentLoaded = false;
    }
}


