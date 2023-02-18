package com.library.booking

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import com.github.tomakehurst.wiremock.client.WireMock.get
import com.github.tomakehurst.wiremock.client.WireMock.ok
import com.github.tomakehurst.wiremock.common.ConsoleNotifier
import com.github.tomakehurst.wiremock.core.WireMockConfiguration
import com.library.booking.db.BookingService
import com.library.booking.dto.BookingDto
import com.library.restdto.WrappedResponse
import io.restassured.RestAssured
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.*
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import wiremock.com.fasterxml.jackson.databind.ObjectMapper
import javax.annotation.PostConstruct

@ActiveProfiles("BookingRestApiTest", "test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration(initializers = [(BookingRestApiTest.Companion.Initializer::class)])
class BookingRestApiTest {

    @LocalServerPort
    protected var port = 0

    @Autowired
    private lateinit var bookingService: BookingService

    companion object {

        private lateinit var wiremockServer: WireMockServer

        @BeforeAll
        @JvmStatic
        fun initClass() {
            wiremockServer = WireMockServer(
                WireMockConfiguration
                    .wireMockConfig()
                    .dynamicPort()
                    .notifier(ConsoleNotifier(true))
            )
            wiremockServer.start()

            val bookRes = (WrappedResponse(code = 200, data = DtoMocks.getBook()).validated())
            val bookingRes = (WrappedResponse(code = 200, data = DtoMocks.getBooking()).validated())

            wiremockServer.stubFor(
                get(WireMock.urlMatching("/api/booking/"))
                    .willReturn(
                        ok(ObjectMapper().writeValueAsString(bookingRes))
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                    )
            )

            wiremockServer.stubFor(
                get(WireMock.urlMatching("/api/book/"))
                    .willReturn(
                        ok(ObjectMapper().writeValueAsString(bookRes))
                            .withHeader("Content-Type", "application/json; charset=utf-8")
                    )
            )
        }

        class Initializer : ApplicationContextInitializer<ConfigurableApplicationContext> {
            override fun initialize(configurableApplicationContext: ConfigurableApplicationContext) {
                TestPropertyValues.of(
                    "bookingServiceAddress: localhost:${wiremockServer.port()}",
                    "bookServiceAddress: localhost:${wiremockServer.port()}"
                )
                    .applyTo(configurableApplicationContext.environment)
            }
        }
    }

    @PostConstruct
    fun init() {
        RestAssured.baseURI = "http://localhost"
        RestAssured.port = port
        RestAssured.basePath = "/api/booking"
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()
    }

    @Test
    fun testGetBookings(){
        RestAssured.given()
            .accept(ContentType.JSON)
            .get()
            .then()
            .statusCode(200)
            .body("data.list.size()", equalTo(5))
    }

    @Test
    fun testGetBookingById(){

        val dto = BookingDto(0, 2, 1, 7)

        val dtoId = RestAssured.given()
            .accept(ContentType.JSON)
            .contentType(ContentType.JSON)
            .body(dto)
            .post()
            .then()
            .statusCode(200)
            .extract().body().jsonPath().getLong("data.bookingId")

        // Get by earlier posted ID and check values against dto object
        RestAssured.given()
            .accept(ContentType.JSON)
            .get("/$dtoId")
            .then()
            .statusCode(200)
            .body("data.bookId", equalTo(dto.bookId!!.toInt()))
    }


}
