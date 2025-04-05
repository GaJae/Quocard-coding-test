package com.example.BookNest.controller

import com.example.BookNest.service.AuthorService
import com.example.db.tables.records.AuthorsRecord
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAllAuthors(): List<AuthorsRecord> = authorService.getAllAuthors()

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Int): AuthorsRecord? = authorService.getAuthorById(id)

    @PostMapping
    fun createAuthor(@RequestBody author: AuthorsRecord): AuthorsRecord = authorService.createAuthor(author)

    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Int, @RequestBody author: AuthorsRecord): AuthorsRecord = authorService.updateAuthor(id, author)

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Int): Boolean = authorService.deleteAuthor(id)
}