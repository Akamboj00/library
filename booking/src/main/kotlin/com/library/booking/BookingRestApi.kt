package com.library.booking

import com.library.booking.BookingDtoConverter.transform
import com.library.booking.db.BookingService
import com.library.booking.dto.BookingDto
import com.library.booksdto.BookDto
import com.library.restdto.RestResponseFactory
import com.library.restdto.WrappedResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(value = "/api/booking", description = "Manages bookings and data related to bookings")
@RestController
@RequestMapping(path = ["/api/booking"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
class BookingRestApi(
    private val bookingService: BookingService
) {

    @ApiOperation("Retrieve all saved bookings")
    @GetMapping
    fun getBookings(): ResponseEntity<WrappedResponse<List<BookingDto>>>{
        return RestResponseFactory.payload(
            200,
            bookingService.getAllBookings().map { transform(it) }
        )
    }

    @ApiOperation("Retrieve a booking based on ID")
    @GetMapping(path = ["/{bookingId}"])
    fun getBookingById(@PathVariable("bookingId") bookingId:Long): ResponseEntity<WrappedResponse<BookingDto>>{
        val booking = bookingService.findBookingByIdEager(bookingId)
        return if(booking !== null){
            RestResponseFactory.payload(
                200,
                transform(booking)
            )
        } else{
            RestResponseFactory.notFound<BookingDto>("Could not find the booking with the given id $bookingId")
        }
    }

    @ApiOperation("Creates a new booking, checks if book quantity and that library id's are different")
    @PostMapping(consumes = [(MediaType.APPLICATION_JSON_UTF8_VALUE)])
    fun postBooking(
        @ApiParam("Booking DTO object, that has to contain all properties for ID")
        @RequestBody
        dto: BookingDto
    ): ResponseEntity<WrappedResponse<BookingDto>> {
        val book: BookDto

        try {
            book = bookingService.fetchBook(dto.bookId!!)

            if (book.numberOfCopies!! <= 0) {
                return RestResponseFactory.userFailure("There are no available books")
            }
        }catch (e:Exception){
            val res = WrappedResponse<Void>(500, message = "Something went wrong when creating booking").validated()
            ResponseEntity.status(500).body(res)
        }
        val dtoWithId: BookingDto

        try {
            val bookingEntity = bookingService.insertBooking(transform(dto))
            dtoWithId = transform(bookingEntity)
        }catch (e: Exception){
            return RestResponseFactory.userFailure("Could not insert in database, please check values")


        }
        return RestResponseFactory.payload(200, dtoWithId)
    }

    @ApiOperation("Delete an existing booking based on ID")
    @DeleteMapping(path = ["/{bookingId}"])
    fun deleteBooking(@PathVariable("bookingId") bookingId: Long): ResponseEntity<WrappedResponse<Void>>{
        return try {
            if(bookingService.deleteBooking(bookingId)){
                RestResponseFactory.noPayload(200)
            }else{
                RestResponseFactory.notFound("No booking with given ID")
            }
        }catch (e: Exception){
            val res = WrappedResponse<Void>(500, message = "Somethin went wrong when deleting booking").validated()
            ResponseEntity.status(500).body(res)
        }
    }

}