package com.library.shipping.db

import com.library.shipping.dto.ShippingDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
interface ShippingRepository : JpaRepository<Shipping, Long>

@Service
@Transactional
class ShippingService(
    private val shippingRepository: ShippingRepository,
    private val em: EntityManager
) {

    fun registerNewShipping(shipping: ShippingDto): Boolean{
        if(shippingRepository.existsById(shipping.shippingId)){
            return false
        }

        val shipment = Shipping()
        shipment.shippingId = shipping.shippingId
        shipment.numberOfCopies = shipping.numberOfCopies
        shipment.originLibId = shipping.originLibId
        shipment.destinationLibId = shipping.destinationLibId
        shippingRepository.save(shipment)
        return true
    }

}