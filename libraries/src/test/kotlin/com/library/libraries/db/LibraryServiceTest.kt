package com.library.libraries.db

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension


@ActiveProfiles("test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
internal class LibraryServiceTest {

    @Autowired
    private lateinit var libraryService: LibraryService

    @Test
    fun testGetAllLibraries(){
        val libs = libraryService.getAllLibraries()

        assertEquals(libs.size, 5)

    }

    @Test
    fun testGetLibraryById(){
        val serviceLib = libraryService.findByLibraryIdEager(1)

        assertNotNull(serviceLib)
    }
}