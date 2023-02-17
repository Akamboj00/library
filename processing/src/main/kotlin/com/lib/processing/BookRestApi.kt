package com.lib.processing

import com.cardgame.booksdto.BookDto
import com.cardgame.restdto.RestResponseFactory
import com.cardgame.restdto.WrappedResponse
import com.lib.processing.db.BookService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "/api/book", description = "Served book data")
@RestController
@RequestMapping(path = ["/api/book"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
class BookRestApi(
    private val bookService: BookService
) {

    @ApiOperation("Retrieve data from all saved books")
    @GetMapping
    fun getBoats(): ResponseEntity<WrappedResponse<List<BookDto>>> {
        return RestResponseFactory.payload(
            200,
            bookService.getAllBooks().map { BookDtoConverter.transform(it) }
        )
    }

    @ApiOperation("Retrieve a book entity based on its ID")
    @GetMapping(path = ["/{bookId}"])
    fun getBookById(
        @PathVariable("bookId") bookId: Long
    ): ResponseEntity<WrappedResponse<BookDto>>{
        val book = bookService.findBookByIdEager(bookId)
        if(book == null){
            return ResponseEntity.notFound().build()
        }

        return RestResponseFactory.payload(200, BookDtoConverter.transform(book))
    }
}