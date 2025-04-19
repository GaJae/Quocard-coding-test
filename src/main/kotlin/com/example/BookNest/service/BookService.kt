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
    fun getAllBooks(): List<BooksRecord> {
        // 실제 데이터베이스 또는 더미 데이터를 반환하도록 구현
        return bookRepository.findAll()
    }

    fun updateBook(id: Int, book: BooksRecord): BooksRecord {
        val existingBook = bookRepository.findById(id) ?: throw IllegalArgumentException("Book not found")
        if (existingBook.status == "出版済み" && book.status == "未出版") {
            throw IllegalStateException("Cannot change status from '出版済み' to '未出版'")
        }
        book.id = id
        return bookRepository.update(book)
    }

    fun deleteBook(id: Int): Boolean {
        return bookRepository.deleteById(id)
    }
}