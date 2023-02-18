package com.library.libraries.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager

@Repository
interface LibraryRepository : JpaRepository<Library, Long>

@Service
@Transactional
class LibraryService (
    private val libraryRepository: LibraryRepository,
    private val em: EntityManager
){
    fun findByLibraryIdEager(libraryId: Long): Library?{
        return libraryRepository.findById(libraryId).orElse(null)
    }

    fun getAllLibraries(): List<Library>{
        return libraryRepository.findAll()
    }
}