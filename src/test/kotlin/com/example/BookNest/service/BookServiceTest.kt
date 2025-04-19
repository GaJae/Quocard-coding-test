package com.example.BookNest.service

import com.example.BookNest.repository.BookRepository
import com.example.db.tables.records.BooksRecord
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
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

    private lateinit var bookService: BookService

    @BeforeEach
    fun setUp() {
        bookService = BookService(bookRepository)
    }

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

    @Test
    fun `deleteBook should remove the book`() {
        `when`(bookRepository.deleteById(1)).thenReturn(true)

        val result = bookService.deleteBook(1)

        assertTrue(result)
        verify(bookRepository).deleteById(1)
    }

    @Test
    fun `getAllBooks should return all books`() {
        val book1 = BooksRecord(1, "Book One", 1000, "UNPUBLISHED")
        val book2 = BooksRecord(2, "Book Two", 1500, "PUBLISHED")
        `when`(bookRepository.findAll()).thenReturn(listOf(book1, book2))

        val result = bookService.getAllBooks()

        assertEquals(2, result.size)
        assertEquals("Book One", result[0].title)
        assertEquals("Book Two", result[1].title)
        verify(bookRepository).findAll()
    }
}