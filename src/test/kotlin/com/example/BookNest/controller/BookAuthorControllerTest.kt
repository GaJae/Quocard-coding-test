package com.example.BookNest.controller

import com.example.BookNest.service.BookAuthorService
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BookAuthorController::class)
class BookAuthorControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookAuthorService: BookAuthorService

    @Test
    fun `addBookAuthor should return success response`() {
        `when`(bookAuthorService.addBookAuthor(1L, 2L)).thenReturn(true)

        mockMvc.perform(
            post("/api/book-author")
                .param("bookId", "1")
                .param("authorId", "2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().string("Book-Author relation added successfully."))
    }

    @Test
    fun `addBookAuthor should return failure response`() {
        `when`(bookAuthorService.addBookAuthor(1L, 2L)).thenReturn(false)

        mockMvc.perform(
            post("/api/book-author")
                .param("bookId", "1")
                .param("authorId", "2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isBadRequest)
            .andExpect(content().string("Failed to add Book-Author relation."))
    }

    @Test
    fun `getAuthorsByBookId should return list of author IDs`() {
        `when`(bookAuthorService.getAuthorsByBookId(1L)).thenReturn(listOf(2L, 3L))

        mockMvc.perform(
            get("/api/book-author/authors")
                .param("bookId", "1")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json("[2, 3]"))
    }

    @Test
    fun `getBooksByAuthorId should return list of book IDs`() {
        `when`(bookAuthorService.getBooksByAuthorId(2L)).thenReturn(listOf(1L, 4L))

        mockMvc.perform(
            get("/api/book-author/books")
                .param("authorId", "2")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(content().json("[1, 4]"))
    }
}