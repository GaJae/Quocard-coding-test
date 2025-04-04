// BookService.kt
package com.example.BookNest.BookService

import com.example.BookNest.repository.BookRepository
import com.example.db.tables.records.BookRecord
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun saveBook(book: BookRecord): BookRecord {
        return bookRepository.save(book)
    }

    fun getBooksByAuthor(authorId: Long): List<BookRecord> {
        return bookRepository.findByAuthorId(authorId)
    }
}