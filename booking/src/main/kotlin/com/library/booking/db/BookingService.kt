package com.library.booking.db

import com.library.booksdto.BookDto
import com.library.booksdto.LibraryDto
import com.library.restdto.WrappedResponse
import org.springframework.beans.factory.annotation.Value
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory
import org.springframework.cloud.client.circuitbreaker.CircuitBreaker
import org.springframework.core.ParameterizedTypeReference
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.http.HttpMethod
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder
import java.io.IOException
import java.net.URI
import javax.annotation.PostConstruct
import javax.persistence.EntityManager

@Repository
interface BookingRepository : JpaRepository<Booking, Long>

@Service
@Transactional
class BookingService(
    private val circuitBreakerFactory: Resilience4JCircuitBreakerFactory,
    private val bookingRepository : BookingRepository,
    private val em: EntityManager
){

    private val client: RestTemplate = RestTemplate()
    private lateinit var circuitBreaker: CircuitBreaker

    @Value("\${bookServiceAddress}")
    private lateinit var bookAddress: String

    private val lock = Any()

    @PostConstruct
    fun init(){
        circuitBreaker = circuitBreakerFactory.create("bookingServiceCircuitBreaker")
    }

    private fun <T> validateResponse(response: ResponseEntity<WrappedResponse<T>>) {
        if (response.statusCodeValue != 200) {
            throw IOException("Bad response from service: ${response.body.message} (${response.statusCodeValue})")
        }

        if(response.body.data == null) {
            throw IOException("Fetched data was not parsed correctly")
        }
    }

    protected fun fetchBook(uri: URI): BookDto{
        val response = circuitBreaker.run(
            {
                client.exchange(uri, HttpMethod.GET, null, object : ParameterizedTypeReference<WrappedResponse<BookDto>>() {})
            },
            {
                throw IOException("Failed to fetch book from service")
                null
            }
        ) ?: throw IOException("No response from booking service")

        validateResponse(response)
        return response.body.data!!
    }

    protected fun fetchLibrary(uri: URI) : LibraryDto{

        val response = circuitBreaker.run(
            {
            client.exchange(uri, HttpMethod.GET, null, object : ParameterizedTypeReference<WrappedResponse<LibraryDto>>(){})
            },
            {
            throw IOException("Failed to fetch library from service")
            null
            }
        ) ?: throw IOException("No response from library service")

        validateResponse(response)
        return response.body.data!!
    }

    private fun buildUri(address: String, endpoint: String, pathValue: String): URI {
        val uri = UriComponentsBuilder
            .fromUriString("http://${address.trim()}/$endpoint$pathValue")
            .build().toUri()
        return uri
    }

    fun fetchBook(bookId: Long): BookDto{
        val uri = buildUri(bookAddress, "api/book", "${bookId}")
        return fetchBook(uri)
    }

    fun findBookingByIdEager(bookingId: Long): Booking?{
        return bookingRepository.findById(bookingId).orElse(null)
    }

    fun getAllBookings(): List<Booking>{
        return bookingRepository.findAll()
    }

    fun insertBooking(booking: Booking): Booking{
        validateBooking(booking)
        return bookingRepository.save(booking)
    }

    fun deleteBooking(bookingId: Long): Boolean {
        val booking = findBookingByIdEager(bookingId)
        if(booking !== null){
            bookingRepository.delete(booking)
            return true
        }
        return false
    }

    fun putBooking(booking: Booking): Booking{
        validateBooking(booking)
        return bookingRepository.save(booking)
    }

    fun validateBooking(booking: Booking, includeId: Boolean = false){
        if(includeId){
            if(booking.bookingId == null){
                throw IllegalStateException("Booking has missing ID")
            }
        }

        if(booking.bookId == null
            || booking.originLibId == null
            || booking.destinationLibId == null){
            throw IllegalStateException("Booking object has missing data")
        }

        if(booking.originLibId == booking.destinationLibId){
            throw IllegalStateException("Origin library and destination Library have to contain different IDs")
        }
    }
}