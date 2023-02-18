package com.library.libraries

import com.library.booksdto.LibraryDto
import com.library.libraries.db.LibraryService
import com.library.restdto.RestResponseFactory
import com.library.restdto.WrappedResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Api(value = "/api/libraries", description = "Serves data related to libraries")
@RestController
@RequestMapping(path = ["/api/libraries"], produces = [(MediaType.APPLICATION_JSON_VALUE)])
class LibrariesRestApi(
    private val libraryService: LibraryService
){
    @ApiOperation("Retrieves all libraries saved in db")
    @GetMapping
    fun getLibraries(): ResponseEntity<WrappedResponse<List<LibraryDto>>>{
        return RestResponseFactory.payload(
            200,
            libraryService.getAllLibraries().map { LibraryDtoConverter.transform(it) }
        )
    }

    @GetMapping(path = ["/{libraryId}"])
    @ApiOperation("Retrieves a library entity based on ID")
    fun getLibraryById(
        @PathVariable("libraryId") libraryId: Long
    ): ResponseEntity<WrappedResponse<LibraryDto>> {
        val lib = libraryService.findByLibraryIdEager(libraryId) ?: return ResponseEntity.notFound().build()

        return RestResponseFactory.payload(200, LibraryDtoConverter.transform(lib))
    }

}