package com.library.booking.db

import org.jetbrains.annotations.NotNull
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Booking {

    @get:Id
    @get:GeneratedValue
    var bookingId: Long? = null

    @get:NotNull
    var originLibId: Long? = null

    @get:NotNull
    var destinationLibId: Long? = null

    @get:NotBlank
    var bookId: Long? = null

}