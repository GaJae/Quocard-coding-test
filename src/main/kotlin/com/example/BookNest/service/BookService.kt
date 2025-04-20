// BookService.kt
package com.example.BookNest.service

import com.example.BookNest.dto.BookDTO
import com.example.BookNest.repository.BookRepository
import com.example.db.tables.records.BooksRecord
import org.springframework.stereotype.Service

@Service
class BookService(private val bookRepository: BookRepository) {

    fun saveBook(book: BookDTO): BooksRecord {
        return bookRepository.save(book)
    }


    fun getBookById(id: Int): BooksRecord? {
        return bookRepository.findById(id)
    }
 fun getAllBooks(): List<BookDTO> =
     bookRepository.findAll().map { book ->
         BookDTO(
             id = book.id,
             title = book.title,
             price = book.price,
             status = book.status
         )
     }

 fun updateBook(id: Int, book: BookDTO): BooksRecord {
     val existingBook = bookRepository.findById(id) ?: throw IllegalArgumentException("Book not found")
     if (existingBook.status == "出版済み" && book.status == "未出版") {
         throw IllegalStateException("Cannot change status from '出版済み' to '未出版'")
     }
     return bookRepository.update(id, book)
 }

    fun deleteBook(id: Int): Boolean {
        return bookRepository.deleteById(id)
    }
}