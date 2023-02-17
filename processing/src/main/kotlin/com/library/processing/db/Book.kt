package com.library.processing.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank

@Entity
class Book(

    @get:Id @get:GeneratedValue
    var bookId : Long? = null,

    @get:NotBlank
    var name: String? = null,

    @get:NotBlank
    var author: String? = null,

    @get:Min(1)
    var numberOfCopies : Int? = null
)