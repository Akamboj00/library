package com.library.libraries

import com.library.libraries.db.LibraryRepository
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.equalTo
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import javax.annotation.PostConstruct


@ActiveProfiles("LibrariesRestApiTest", "Test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class LibrariesRestApiTest {

    @LocalServerPort
    protected var port = 0

    @Autowired
    private lateinit var libraryRepository: LibraryRepository

    @PostConstruct
    fun init(){
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun getLibrariesTest(){

        RestAssured.given()
            .accept(ContentType.JSON)
            .get("/api/libraries")
            .then()
            .statusCode(200)
            .body("data.list.size()", equalTo(5))
    }

    @Test
    fun getLibraryById(){
        RestAssured.given()
            .accept(ContentType.JSON)
            .get("/api/libraries/1")
            .then()
            .statusCode(200)
            .body("data.name", equalTo("Papirbredden"))
    }
}