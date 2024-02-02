package com.example.myapplication
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.ItemRowBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ItemRowBinding
    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)

        // Setting the background color of the toolbar to blue
        toolbar.setBackgroundColor(getColor(android.R.color.holo_blue_dark))

        // Setting the toolbar as the app bar
        setSupportActionBar(toolbar)
        // Finding views by their IDs
        val searchView: SearchView = findViewById(R.id.searchView)
        recyclerView = findViewById(R.id.recyclerView)

        // Setting up RecyclerView
        itemListAdapter = ItemListAdapter()
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = itemListAdapter

        // Setting up search functionality
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                itemListAdapter.filter.filter(newText)
                return false
            }
        })

        // Fetching data from API
        fetchData()
    }

    private fun fetchData() {
        GlobalScope.launch(Dispatchers.IO) {
            val url = URL("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            val connection = url.openConnection() as HttpURLConnection
            val response = StringBuilder()
            val reader = BufferedReader(InputStreamReader(connection.inputStream))

            var line: String?
            while (reader.readLine().also { line = it } != null) {
                response.append(line)
            }
            reader.close()

            val jsonArray = JSONArray(response.toString())

            val items = mutableListOf<Item>()
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val id = jsonObject.optInt("id")
                val listId = jsonObject.optInt("listId")
                val name = jsonObject.optString("name")
                if (name != "null" && name.isNotBlank()) {
                    items.add(Item(id, listId, name))
                }
            }

            val sortedItems = items.sortedWith(compareBy({ it.listId }, { it.name }))
            val groupedItems = sortedItems.groupBy { it.listId }

            launch(Dispatchers.Main) {
                itemListAdapter.setItems(groupedItems)
            }
        }
    }
}
