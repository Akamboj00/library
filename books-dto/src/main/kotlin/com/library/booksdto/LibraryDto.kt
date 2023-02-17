package com.library.booksdto

import io.swagger.annotations.ApiModelProperty

class LibraryDto(

    @get:ApiModelProperty("ID of the library")
    var libraryId: Long? = null,

    @get:ApiModelProperty("Name of the library")
    var name: String? = null,

    @get:ApiModelProperty("The city that the library resides in")
    var city: String? = null
)