package com.library.shipping.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Min

@Entity
class Shipping {

    @get:Id
    @get:GeneratedValue
    var shippingId: Long? = null

    @get:Id
    var originLibId: Long? = null

    @get:Id
    var destinationLibId: Long? = null

    @get:Min(1)
    var numberOfCopies : Int? = null

}