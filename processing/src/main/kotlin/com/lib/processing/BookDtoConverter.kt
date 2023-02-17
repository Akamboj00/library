package com.lib.processing

import com.cardgame.booksdto.BookDto
import com.lib.processing.db.Book

object BookDtoConverter {
    fun transform(book: Book): BookDto {
        return book.run { BookDto(bookId, name, author, numberOfCopies) }
    }

    fun transform(dto: BookDto): Book {
        return Book().apply {
            bookId = dto.bookId
            name = dto.name
            author = dto.author
            numberOfCopies = dto.numberOfCopies
        }
    }
}