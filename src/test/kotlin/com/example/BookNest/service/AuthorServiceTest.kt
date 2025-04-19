package com.example.BookNest.service

import com.example.BookNest.repository.AuthorRepository
import com.example.db.tables.records.AuthorsRecord
import org.junit.jupiter.api.Assertions.*
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

    private val authorService = AuthorService(authorRepository)

    @Test
    fun `createAuthor should return created author`() {
        val author = AuthorsRecord(1, "Test Author", LocalDate.parse("1980-01-01"))
        `when`(authorRepository.save(author)).thenReturn(author)

        val result = authorService.createAuthor(author)

        assertEquals(author, result)
        verify(authorRepository).save(author)
    }

    @Test
    fun `getAuthorById should return author when found`() {
       val author = AuthorsRecord(1, "Test Author", LocalDate.parse("1980-01-01"))
        `when`(authorRepository.findById(1)).thenReturn(author)

        val result = authorService.getAuthorById(1)

        assertEquals(author, result)
        verify(authorRepository).findById(1)
    }
}