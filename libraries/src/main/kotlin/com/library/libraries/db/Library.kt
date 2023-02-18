package com.library.libraries.db

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.validation.constraints.NotBlank

@Entity
class Library {

    @get:Id
    @get:GeneratedValue
    var libraryId: Long? = null

    @get:NotBlank
    var name: String? = null

    @get:NotBlank
    var city: String? = null
}
