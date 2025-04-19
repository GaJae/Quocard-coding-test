package com.example.BookNest.controller

import com.example.BookNest.dto.BookDTO
import com.example.BookNest.service.BookService
import com.example.db.tables.records.BooksRecord
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*


@ExtendWith(MockitoExtension::class)
class BookControllerTest {

    private lateinit var mockMvc: MockMvc

    @Mock
    private lateinit var bookService: BookService

    @InjectMocks
    private lateinit var bookController: BookController

    @Test
    fun `createBook should return created book`() {
        // MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()

        // Given
        val bookDTO = BookDTO("Book 1", 1000, "未出版")
        val book = BooksRecord(1, "Book 1", 1000, "未出版")
        `when`(bookService.saveBook(book = book)).thenReturn(book)

        // When & Then
        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"Book 1","price":1000,"status":"未出版"}""")
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.title").value("Book 1"))
            .andExpect(jsonPath("$.price").value(1000))
            .andExpect(jsonPath("$.status").value("未出版"))
    }

    @Test
    fun `getAllBooks should return list of books`() {
        // MockMvc 설정
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build()

        // Given
        val books = listOf(
            BooksRecord(1, "Book 1", 1000, "未出版"),
            BooksRecord(2, "Book 2", 1500, "出版済み")
        )
        `when`(bookService.getAllBooks()).thenReturn(books)

        // When & Then
        mockMvc.perform(get("/books"))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].id").value(1))
            .andExpect(jsonPath("$[0].title").value("Book 1"))
            .andExpect(jsonPath("$[1].id").value(2))
            .andExpect(jsonPath("$[1].title").value("Book 2"))
    }
}