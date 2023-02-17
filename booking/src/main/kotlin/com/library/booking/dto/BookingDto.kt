package com.library.booking.dto

import io.swagger.annotations.ApiModelProperty

data class BookingDto(

    @get:ApiModelProperty("ID of the booking")
    var bookingId: Long? = null,

    @get:ApiModelProperty("ID of the origin library of the booking")
    var originLibId: Long? = null,

    @get:ApiModelProperty("ID of the destination library of the booking")
    var destinationLibId: Long? = null,

    @get:ApiModelProperty("ID of the book to be shipped")
    var bookId: Long? = null

)