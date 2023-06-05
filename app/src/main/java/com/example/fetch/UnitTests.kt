package com.example.fetch

import org.junit.Assert.assertEquals
import org.junit.Test

class UnitTests {


    @Test
    fun testMergeSort() {
        val parser = FetchDataParser()

        val items = mutableListOf(
            Item(58, "Item 58", 1),
            Item(50, "Item 50", 1),
            Item(58, "Item 58", 2),
            Item(70, "Item 70", 1)
        )

        val sortedItems = parser.mergeSort(items)

        assertEquals("Item 50", sortedItems[0].name)
        assertEquals("Item 58", sortedItems[1].name)
        assertEquals("Item 70", sortedItems[2].name)
        assertEquals("Item 58", sortedItems[3].name)
    }

    @Test
    fun testCompareItems() {
        val parser = FetchDataParser()

        val item1 = Item(1, "Item 50", 1)
        val item2 = Item(2, "Item 58", 1)
        val item3 = Item(3, "Item 58", 2)

        assertEquals(-1, parser.compareItems(item1, item2))
        assertEquals(1, parser.compareItems(item2, item1))
        assertEquals(-1, parser.compareItems(item2, item3))
        assertEquals(1, parser.compareItems(item3, item2))
    }
}