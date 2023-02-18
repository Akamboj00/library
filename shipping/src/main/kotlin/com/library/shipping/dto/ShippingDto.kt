package com.library.shipping.dto

import io.swagger.annotations.ApiModelProperty

class ShippingDto(

    @get:ApiModelProperty("The id of the Shipping")
    var shippingId: Long? = null,

    @get:ApiModelProperty("The id of the origin library")
    var originLibId: Long? = null,

    @get:ApiModelProperty("The id of the destination library")
    var destinationLibId: Long? = null,

    @get:ApiModelProperty("Number of copies to be shipped")
    var numberOfCopies : Int? = null
)