package com.example.BookNest.service

import com.example.BookNest.repository.BookRepository
import com.example.db.tables.records.BooksRecord
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockitoExtension::class)
class BookServiceTest {

    @Mock
    private lateinit var bookRepository: BookRepository

    private val bookService = BookService(bookRepository)

    @Test
    fun `saveBook should return saved book`() {
        val book = BooksRecord(1, "Test Book", 1000, "UNPUBLISHED")
        `when`(bookRepository.save(book)).thenReturn(book)

        val result = bookService.saveBook(book)

        assertEquals(book, result)
        verify(bookRepository).save(book)
    }

    @Test
    fun `getBookById should return book when found`() {
        val book = BooksRecord(1, "Test Book", 1000, "UNPUBLISHED")
        `when`(bookRepository.findById(1)).thenReturn(book)

        val result = bookService.getBookById(1)

        assertEquals(book, result)
        verify(bookRepository).findById(1)
    }

    @Test
    fun `updateBook should throw exception when changing status from '出版済み' to '未出版'`() {
        val book = BooksRecord(1, "Book 1", 1000, "出版済み")
        `when`(bookService.getBookById(1)).thenReturn(book)

        val exception = assertThrows<IllegalStateException> {
            bookService.updateBook(1, BooksRecord(1, "Book 1", 1000, "未出版"))
        }

        assertEquals("Cannot change status from '出版済み' to '未出版'", exception.message)
    }
}