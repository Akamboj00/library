package com.library.booksdto

import io.swagger.annotations.ApiModelProperty

class BookDto(

    @get:ApiModelProperty("The id of the book")
    var bookId: Long? = null,

    @get:ApiModelProperty("The name of this book")
    var name: String? = null,

    @get:ApiModelProperty("The author of this book")
    var author: String? = null,

    @get:ApiModelProperty("Amount of books")
    var numberOfCopies: Int? = null

)