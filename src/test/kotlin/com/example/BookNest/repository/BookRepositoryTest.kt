package com.example.BookNest.repository

import com.example.db.tables.records.BooksRecord
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import

@JooqTest
@Import(BookRepository::class)
class BookRepositoryTest @Autowired constructor(
    private val bookRepository: BookRepository,
    private val dslContext: DSLContext
) {

    @BeforeEach
    fun setUp() {
        dslContext.execute("CREATE SCHEMA IF NOT EXISTS public")
        dslContext.execute("""
            CREATE TABLE IF NOT EXISTS public.books (
                id SERIAL PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                price INT NOT NULL,
                status VARCHAR(50) NOT NULL
            )
        """)
        dslContext.execute("TRUNCATE TABLE public.books")
    }

    @Test
    fun `findAll returns all books`() {
        val book1 = BooksRecord().apply {
            title = "Book One"
            price = 1000
            status = "Available"
        }
        val book2 = BooksRecord().apply {
            title = "Book Two"
            price = 2000
            status = "Sold Out"
        }
        bookRepository.save(book1)
        bookRepository.save(book2)

        val books = bookRepository.findAll()

        assertEquals(2, books.size)
        assertEquals("Book One", books[0].title)
        assertEquals("Book Two", books[1].title)
    }

    @Test
    fun `findById returns the correct book`() {
        val book = BooksRecord().apply {
            title = "Test Book"
            price = 1500
            status = "Available"
        }
        val savedBook = bookRepository.save(book)

        val foundBook = bookRepository.findById(savedBook.id)

        assertNotNull(foundBook)
        assertEquals(savedBook.title, foundBook?.title)
    }

    @Test
    fun `save inserts a new book`() {
        val book = BooksRecord().apply {
            title = "New Book"
            price = 1200
            status = "Available"
        }

        val savedBook = bookRepository.save(book)

        assertNotNull(savedBook)
        assertEquals(book.title, savedBook.title)
    }

    @Test
    fun `update modifies an existing book`() {
        val book = BooksRecord().apply {
            title = "Old Title"
            price = 1000
            status = "Available"
        }
        val savedBook = bookRepository.save(book)

        savedBook.title = "Updated Title"
        val updatedBook = bookRepository.update(savedBook)

        assertEquals("Updated Title", updatedBook.title)
    }

    @Test
    fun `deleteById removes the book`() {
        val book = BooksRecord().apply {
            title = "Book To Delete"
            price = 800
            status = "Available"
        }
        val savedBook = bookRepository.save(book)

        val isDeleted = bookRepository.deleteById(savedBook.id)

        assertTrue(isDeleted)
        assertTrue(bookRepository.findAll().isEmpty())
    }
}