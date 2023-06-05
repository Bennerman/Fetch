package com.example.fetch

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier
import com.example.fetch.ui.theme.FetchTheme
import okhttp3.*
import org.json.JSONArray
import java.io.IOException
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle


class MainActivity : ComponentActivity() {

    // Global list of data to be displayed
    private val itemList = mutableStateListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fetchData()

        setContent {
            FetchTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    DisplayItems(itemList)
                }
            }
        }
    }

    /**
     * Gets data and calls the parser
     * Otherwise prints error
     */
    private fun fetchData() {
        val client = OkHttpClient()
        val request = Request.Builder()
            .url("https://fetch-hiring.s3.amazonaws.com/hiring.json")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val responseData = response.body?.string()
                val parser = FetchDataParser()
                val filteredItems = parser.parseJsonAndFilter(responseData)
                itemList.addAll(filteredItems)
            }
        })
    }
}
//Item class for each data entry
data class Item(val id: Int, val name: String, val listId: Int)

/**
 * Display Items in a singular, scrollable list.
 * Items are displayed by ListId, Name in numerical order
 */
@Composable
fun DisplayItems(items: List<Item>) {
    Column(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        Text(
            text = "Fetch Data",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
        )

        LazyColumn {
            items(items) { item ->
                Column(
                    modifier = Modifier.padding(horizontal = 18.dp, vertical = 8.dp)
                ) {
                    Text(
                        text = "ListId: ${item.listId}",
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "Name: ${item.name}",
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
    }
}




