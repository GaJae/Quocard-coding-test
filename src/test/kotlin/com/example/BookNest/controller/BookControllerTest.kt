package com.example.BookNest.controller

import com.example.BookNest.service.BookService
import com.example.db.tables.records.BooksRecord
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

@WebMvcTest(BookController::class)
class BookControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var bookService: BookService

    @Test
    fun `getAllBooks should return list of books`() {
        val books = listOf(
            BooksRecord(1, "Book 1", 1000, "未出版"),
            BooksRecord(2, "Book 2", 2000, "出版済み")
        )
        `when`(bookService.getAllBooks()).thenReturn(books)

        mockMvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json("""[{"id":1,"title":"Book 1","price":1000,"status":"未出版"},{"id":2,"title":"Book 2","price":2000,"status":"出版済み"}]"""))
    }

    @Test
    fun `getBookById should return book details`() {
        val book = BooksRecord(1, "Book 1", 1000, "未出版")
        `when`(bookService.getBookById(1)).thenReturn(book)

        mockMvc.perform(get("/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id":1,"title":"Book 1","price":1000,"status":"未出版"}"""))
    }

    @Test
    fun `createBook should return created book`() {
        val book = BooksRecord(1, "Book 1", 1000, "未出版")
        `when`(bookService.saveBook(any())).thenReturn(book)

        mockMvc.perform(
            post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"Book 1","price":1000,"status":"未出版"}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id":1,"title":"Book 1","price":1000,"status":"未出版"}"""))
    }

    @Test
    fun `updateBook should return updated book`() {
        val book = BooksRecord(1, "Updated Book", 1500, "出版済み")
        `when`(bookService.updateBook(eq(1), any())).thenReturn(book)

        mockMvc.perform(
            put("/books/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""{"title":"Updated Book","price":1500,"status":"出版済み"}""")
        )
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id":1,"title":"Updated Book","price":1500,"status":"出版済み"}"""))
    }

    @Test
    fun `deleteBook should return success response`() {
        `when`(bookService.deleteBook(1)).thenReturn(true)

        mockMvc.perform(delete("/books/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string("true"))
    }
}