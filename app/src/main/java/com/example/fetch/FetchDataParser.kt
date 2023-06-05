package com.example.fetch

import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject

class FetchDataParser {
    /**
     * Reads in json of data in format:
     * [
     *    {"id": 203, "listId": 2, "name": ""},
     *    {"id": 684, "listId": 1, "name": "Item 684"}
     * ]
     *
     * Gets rid of data where the 'name' field is empty or
     * null
     *
     * Calls mergeSort to sort data
     *
     */
    internal fun parseJsonAndFilter(jsonData: String?): List<Item> {
        val items = mutableListOf<Item>()

        jsonData?.let {
            val jsonArray = JSONArray(it)

            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)
                val name = jsonObject.getString("name")
                val listId = jsonObject.getInt("listId")
                val id = jsonObject.getInt("id")

                if (!name.isEmpty() && name != "null") {
                    items.add(Item(id, name, listId))
                }
            }
        }

        return mergeSort(items)
    }

    /**
     *
     * MergeSort to sort data based on ListId and Name:
     *
     * Items are first sorted by ListId and then by Name.
     *
     * Ex.
     * ListId: 1, Name: Item 50
     * ListId: 1, Name: Item 58
     * ListId: 2, Name: Item 58
     */

    internal fun mergeSort(items: MutableList<Item>): List<Item> {
        if (items.size <= 1) {
            return items
        }

        val mid = items.size / 2
        val left = items.subList(0, mid)
        val right = items.subList(mid, items.size)

        val sortedLeft = mergeSort(left.toMutableList())
        val sortedRight = mergeSort(right.toMutableList())

        return merge(sortedLeft, sortedRight)
    }

    /**
     * Basic merge function to join sorted lists together.
     */

    internal fun merge(left: List<Item>, right: List<Item>): List<Item> {
        var i = 0
        var j = 0
        val mergedList = mutableListOf<Item>()

        while (i < left.size && j < right.size) {
            val compareResult = compareItems(left[i], right[j])

            if (compareResult <= 0) {
                mergedList.add(left[i])
                i++
            } else {
                mergedList.add(right[j])
                j++
            }
        }

        while (i < left.size) {
            mergedList.add(left[i])
            i++
        }

        while (j < right.size) {
            mergedList.add(right[j])
            j++
        }

        return mergedList
    }

    /**
     * Helper function to Merge.
     *
     * Items are compared by first their listId, and if equivalent,
     * compared by their Name.
     *
     * Ex.
     * ListId: 1, Name: Item 50
     * ListId: 1, Name: Item 58
     * ListId: 2, Name: Item 58
     *
     * When ListId: 1, Name: Item 50 when compared to ListId: 1, Name: Item 58
     * returns -1
     *
     */
    internal fun compareItems(item1: Item, item2: Item): Int {
        val listIdComparison = item1.listId.compareTo(item2.listId)
        //Check to see if listID is the same
        if (listIdComparison != 0) {
            return listIdComparison
        }

        //Otherwise compare by id
        val number1 = item1.id
        val number2 = item2.id
        return number1.compareTo(number2)
    }
}