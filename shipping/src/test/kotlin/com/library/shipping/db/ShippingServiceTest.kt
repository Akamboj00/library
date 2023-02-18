package com.library.shipping.db

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension


@ActiveProfiles("ShippingServiceTest","test")
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class ShippingServiceTest {

    @Autowired
    private lateinit var shippingService: ShippingService

    @Autowired
    private lateinit var shippingRepository: ShippingRepository

    @BeforeEach
    fun initTest(){
        shippingRepository.deleteAll()
    }

//    @Test
//    fun testCreateShipment(){
//        val id = "foo"
//        assertTrue()
//    }
}