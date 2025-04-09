package com.example.BookNest.service

import com.example.BookNest.repository.BookAuthorRepository
import org.springframework.stereotype.Service

@Service
class BookAuthorService(private val bookAuthorRepository: BookAuthorRepository) {

    fun addBookAuthor(bookId: Long, authorId: Long): Boolean {
        return bookAuthorRepository.addBookAuthor(bookId, authorId) > 0
    }

    fun removeBookAuthor(bookId: Long, authorId: Long): Boolean {
        return bookAuthorRepository.removeBookAuthor(bookId, authorId) > 0
    }

    fun getAuthorsByBookId(bookId: Long): List<Long> {
        return bookAuthorRepository.findAuthorsByBookId(bookId)
    }

    fun getBooksByAuthorId(authorId: Long): List<Long> {
        return bookAuthorRepository.findBooksByAuthorId(authorId)
    }
}