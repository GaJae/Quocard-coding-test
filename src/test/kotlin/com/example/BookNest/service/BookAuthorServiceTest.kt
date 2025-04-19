package com.example.BookNest.service

import com.example.BookNest.repository.BookAuthorRepository
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockitoExtension::class)
class BookAuthorServiceTest {

    @Mock
    private lateinit var bookAuthorRepository: BookAuthorRepository

    private val bookAuthorService = BookAuthorService(bookAuthorRepository)

    @Test
    fun `addBookAuthor should return true when successful`() {
        `when`(bookAuthorRepository.addBookAuthor(1L, 2L)).thenReturn(1)

        val result = bookAuthorService.addBookAuthor(1L, 2L)

        assertTrue(result)
        verify(bookAuthorRepository).addBookAuthor(1L, 2L)
    }

    @Test
    fun `getAuthorsByBookId should return list of author IDs`() {
        `when`(bookAuthorRepository.findAuthorsByBookId(1L)).thenReturn(listOf(2L, 3L))

        val result = bookAuthorService.getAuthorsByBookId(1L)

        assertEquals(listOf(2L, 3L), result)
        verify(bookAuthorRepository).findAuthorsByBookId(1L)
    }
}