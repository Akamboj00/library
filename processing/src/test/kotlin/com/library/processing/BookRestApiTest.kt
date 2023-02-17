package com.library.processing

import com.library.processing.db.BookRepository
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

@ActiveProfiles("BookRestApiTest", "test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookRestApiTest {

    @LocalServerPort
    protected var port = 0

    @Autowired
    private lateinit var bookRepository: BookRepository

    @PostConstruct
    fun init(){
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun getBooksTest(){

        RestAssured.given()
            .accept(ContentType.JSON)
            .get("/api/book")
            .then()
            .statusCode(200)
            .body("data.list.size()", equalTo(10))
    }


    @Test
    fun getBookById() {
        RestAssured.given()
            .accept(ContentType.JSON)
            .get("/api/book/1")
            .then()
            .statusCode(200)
            .body("data.name", equalTo("1984"))
    }
}