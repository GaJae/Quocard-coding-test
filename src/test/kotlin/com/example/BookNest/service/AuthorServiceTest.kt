package com.example.BookNest.service

import com.example.BookNest.repository.AuthorRepository
import com.example.db.tables.records.AuthorsRecord
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith
import java.time.LocalDate

@ExtendWith(MockitoExtension::class)
class AuthorServiceTest {

    @Mock
    private lateinit var authorRepository: AuthorRepository

    private lateinit var authorService: AuthorService

    @BeforeEach
    fun setUp() {
        authorService = AuthorService(authorRepository)
    }

    @Test
    fun `getAllAuthors should return all authors`() {
        val author1 = AuthorsRecord(1, "Author One", LocalDate.parse("1980-01-01"))
        val author2 = AuthorsRecord(2, "Author Two", LocalDate.parse("1990-01-01"))
        `when`(authorRepository.findAll()).thenReturn(listOf(author1, author2))

        val result = authorService.getAllAuthors()

        assertEquals(2, result.size)
        assertEquals("Author One", result[0].name)
        assertEquals("Author Two", result[1].name)
        verify(authorRepository).findAll()
    }

    @Test
    fun `getAuthorById should return author when found`() {
        val author = AuthorsRecord(1, "Test Author", LocalDate.parse("1980-01-01"))
        `when`(authorRepository.findById(1)).thenReturn(author)

        val result = authorService.getAuthorById(1)

        assertEquals(author, result)
        verify(authorRepository).findById(1)
    }

    @Test
    fun `createAuthor should return created author`() {
        val author = AuthorsRecord(1, "Test Author", LocalDate.parse("1980-01-01"))
        `when`(authorRepository.save(author)).thenReturn(author)

        val result = authorService.createAuthor(author)

        assertEquals(author, result)
        verify(authorRepository).save(author)
    }

//    @Test
//    fun `updateAuthor should modify and return updated author`() {
//        val author = AuthorsRecord(1, "Old Name", LocalDate.parse("1980-01-01"))
//        val updatedAuthor = AuthorsRecord(1, "Updated Name", LocalDate.parse("1980-01-01"))
//        `when`(authorRepository.update(author)).thenReturn(updatedAuthor)
//
//        val result = authorService.updateAuthor(1, author)
//
//        assertEquals("Updated Name", result.name)
//        verify(authorRepository).update(author)
//    }

    @Test
    fun `deleteAuthor should return true when author is deleted`() {
        `when`(authorRepository.deleteById(1)).thenReturn(true)

        val result = authorService.deleteAuthor(1)

        assertTrue(result)
        verify(authorRepository).deleteById(1)
    }
}