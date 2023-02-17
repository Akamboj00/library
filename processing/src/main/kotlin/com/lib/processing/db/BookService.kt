package com.lib.processing.db

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.persistence.EntityManager
import javax.persistence.TypedQuery

@Repository
interface BookRepository : JpaRepository<Book, Long>


@Service
@Transactional
class BookService(
    private val bookRepository: BookRepository,
    private val em: EntityManager
) {

    fun findBookByIdEager(bookId: Long) : Book?{
        return bookRepository.findById(bookId).orElse(null)
    }

    fun getAllBooks(): List<Book>{
        return bookRepository.findAll()
    }

    fun getBooksInStock(numberOfCopies: Int) : List<Book>{
        if(numberOfCopies < 1){
            throw IllegalArgumentException("Book amount cannot be less than 1")
        }

        val query: TypedQuery<Book> = em.createQuery(
            "select b from Book b where b.numberOfCopies >= ?1",
            Book::class.java
        ).setParameter(1, numberOfCopies)

        return query.resultList
    }
}