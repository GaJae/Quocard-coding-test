package com.example.BookNest.controller

import com.example.BookNest.dto.AuthorDTO
import com.example.BookNest.service.AuthorService
import com.example.db.tables.records.AuthorsRecord
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/authors")
class AuthorController(private val authorService: AuthorService) {

    @GetMapping
    fun getAllAuthors(): List<AuthorDTO> {
        return authorService.getAllAuthors().map {
            AuthorDTO(it.id, it.name, it.birthDate)
        }
    }

    @GetMapping("/{id}")
    fun getAuthorById(@PathVariable id: Int): AuthorDTO? {
        val author = authorService.getAuthorById(id)
        return author?.let { AuthorDTO(it.id, author.name, author.birthDate) }
    }

    @PostMapping
    fun createAuthor(@RequestBody author: AuthorsRecord): AuthorDTO {
        val createdAuthor = authorService.createAuthor(author)
        return AuthorDTO(createdAuthor.id, createdAuthor.name, createdAuthor.birthDate)
    }
    @PutMapping("/{id}")
    fun updateAuthor(@PathVariable id: Int, @RequestBody author: AuthorsRecord): AuthorsRecord = authorService.updateAuthor(id, author)

    @DeleteMapping("/{id}")
    fun deleteAuthor(@PathVariable id: Int): Boolean = authorService.deleteAuthor(id)

}