package com.library.shipping

import com.library.restdto.RestResponseFactory
import com.library.restdto.WrappedResponse
import com.library.shipping.db.ShippingService
import com.library.shipping.dto.ShippingDto
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "/api/shipping", description = "Manages shipments and data related to shipments")
@RestController
@RequestMapping(path = ["/api/shipping"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
class ShippingRestApi (
    private val shippingService: ShippingService
) {

    @ApiOperation("Create a new shipment")
    @PutMapping(path = ["/api/shipping"])
    fun createShipment(shippingDto: ShippingDto): ResponseEntity<WrappedResponse<Void>> {
        val ok = shippingService.registerNewShipping(shippingDto)
        return if (!ok) RestResponseFactory.userFailure("Shipping ${shippingDto.shippingId} already exist")
        else RestResponseFactory.noPayload(201)
    }
}
