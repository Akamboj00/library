package com.library.booking

import com.library.booking.dto.BookingDto
import com.library.booksdto.BookDto

object DtoMocks {

    fun getBooking(): BookingDto{return BookingDto(3, 1, 2)}
    fun getBook(): BookDto {return BookDto(1, "1984","George Orwell", 10)
    }
}