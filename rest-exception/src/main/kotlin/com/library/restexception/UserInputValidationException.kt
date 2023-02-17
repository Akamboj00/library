package com.library.restexception

class UserInputValidationException(
    message: String,
    val httpCode : Int = 400
) : RuntimeException(message)