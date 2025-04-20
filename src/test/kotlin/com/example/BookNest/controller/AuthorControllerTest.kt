package com.example.BookNest.controller

import com.example.BookNest.dto.AuthorDTO
import com.example.BookNest.service.AuthorService
import com.example.db.tables.records.AuthorsRecord
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import java.time.LocalDate

@WebMvcTest(AuthorController::class)
class AuthorControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @MockBean
    private lateinit var authorService: AuthorService

    @Test
    fun `getAllAuthors should return list of authors`() {
        val authors = listOf(
            AuthorsRecord(1, "Author 1", LocalDate.parse("1980-01-01")),
            AuthorsRecord(2, "Author 2", LocalDate.parse("1990-01-01"))
        )
        `when`(authorService.getAllAuthors()).thenReturn(authors)

        mockMvc.perform(get("/authors").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json("""[{"id":1,"name":"Author 1","birthDate":"1980-01-01"},{"id":2,"name":"Author 2","birthDate":"1990-01-01"}]"""))
    }

    @Test
    fun `getAuthorById should return author details`() {
        val author = AuthorsRecord(1, "Author 1", LocalDate.parse("1980-01-01"))
        `when`(authorService.getAuthorById(1)).thenReturn(author)

        mockMvc.perform(get("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().json("""{"id":1,"name":"Author 1","birthDate":"1980-01-01"}"""))
    }

// @Test
//     fun `createAuthor should return created author`() {
//         val author = AuthorsRecord(1, "Author 1", LocalDate.parse("1980-01-01"))
//         `when`(authorService.createAuthor(any())).thenReturn(author)
//
//         mockMvc.perform(
//             post("/authors")
//                 .contentType(MediaType.APPLICATION_JSON)
//                 .content("""{"id":1,"name":"Author 1","birthDate":"1980-01-01"}""")
//         )
//             .andExpect(status().isOk)
//             .andExpect(content().json("""{"id":1,"name":"Author 1","birthDate":"1980-01-01"}"""))
//     }

//    @Test
//    fun `updateAuthor should return updated author`() {
//        val author = AuthorsRecord(1, "Updated Author", LocalDate.parse("1980-01-01"))
//        `when`(authorService.updateAuthor(anyInt(), any(AuthorsRecord::class.java))).thenReturn(author)
//
//        mockMvc.perform(
//            put("/authors/1")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("""{"id":1,"name":"Updated Author","birthDate":"1980-01-01"}""")
//        )
//            .andExpect(status().isOk)
//            .andExpect(content().json("""{"id":1,"name":"Updated Author","birthDate":"1980-01-01"}"""))
//    }

    @Test
    fun `deleteAuthor should return success response`() {
        `when`(authorService.deleteAuthor(1)).thenReturn(true)

        mockMvc.perform(delete("/authors/1").contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(content().string("true"))
    }
}