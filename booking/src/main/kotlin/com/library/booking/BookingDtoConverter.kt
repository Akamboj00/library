package com.library.booking

import com.library.booking.db.Booking
import com.library.booking.dto.BookingDto

object BookingDtoConverter {
    fun transform(booking: Booking): BookingDto{
        return booking.run { BookingDto(bookingId, originLibId, destinationLibId, bookId) }
    }

    fun transform(dto: BookingDto): Booking {
        return Booking().apply {
            bookingId = dto.bookingId
            originLibId = dto.originLibId
            destinationLibId = dto.destinationLibId
            bookId = dto.bookId
        }
    }

}