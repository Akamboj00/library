package com.cardgame.restexception

class UserInputValidationException(
    message: String,
    val httpCode : Int = 400
) : RuntimeException(message)