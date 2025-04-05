// BookService.kt
package com.example.BookNest.service

import com.example.BookNest.repository.BookRepository
import com.example.db.tables.records.BooksRecord
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun saveBook(book: BooksRecord): BooksRecord {
        return bookRepository.save(book)
    }


    fun getBookById(id: Int): BooksRecord? {
        return bookRepository.findById(id)
    }

    fun updateBook(id: Int, book: BooksRecord): BooksRecord {
        book.id = id
        return bookRepository.update(book)
    }

    fun deleteBook(id: Int): Boolean {
        return bookRepository.deleteById(id)
    }
}