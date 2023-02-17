package com.library.processing.db

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension

@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class BookServiceTest {

    @Autowired
    private lateinit var bookService: BookService

    @Test
    fun testGetAllBooks() {
        val books = bookService.getAllBooks()

        assertEquals(books.size, 10)
    }

}
