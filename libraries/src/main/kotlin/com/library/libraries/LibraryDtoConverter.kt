package com.library.libraries

import com.library.booksdto.LibraryDto
import com.library.libraries.db.Library

object LibraryDtoConverter {

    fun transform(library: Library): LibraryDto {
        return library.run { LibraryDto(libraryId, name, city) }
    }

    fun transform(dto: LibraryDto): Library {
        return Library().apply {
            libraryId = dto.libraryId
            name = dto.name
            city = dto.name
        }
    }
}