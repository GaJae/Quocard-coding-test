package com.example.BookNest.controller

import com.example.BookNest.service.BookAuthorService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/book-author")
class BookAuthorController(private val bookAuthorService: BookAuthorService) {

    @PostMapping
    fun addBookAuthor(@RequestParam bookId: Long, @RequestParam authorId: Long): ResponseEntity<String> {
        return if (bookAuthorService.addBookAuthor(bookId, authorId)) {
            ResponseEntity.ok("Book-Author relation added successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to add Book-Author relation.")
        }
    }

    @DeleteMapping
    fun removeBookAuthor(@RequestParam bookId: Long, @RequestParam authorId: Long): ResponseEntity<String> {
        return if (bookAuthorService.removeBookAuthor(bookId, authorId)) {
            ResponseEntity.ok("Book-Author relation removed successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to remove Book-Author relation.")
        }
    }

    @GetMapping("/authors")
    fun getAuthorsByBookId(@RequestParam bookId: Long): ResponseEntity<List<Long>> {
        return ResponseEntity.ok(bookAuthorService.getAuthorsByBookId(bookId))
    }

    @GetMapping("/books")
    fun getBooksByAuthorId(@RequestParam authorId: Long): ResponseEntity<List<Long>> {
        return ResponseEntity.ok(bookAuthorService.getBooksByAuthorId(authorId))
    }

    @PutMapping
    fun updateBookAuthor(
        @RequestParam bookId: Long,
        @RequestParam authorId: Long,
        @RequestParam newAuthorId: Long
    ): ResponseEntity<String> {
        return if (bookAuthorService.updateBookAuthor(bookId, authorId, newAuthorId)) {
            ResponseEntity.ok("Book-Author relation updated successfully.")
        } else {
            ResponseEntity.badRequest().body("Failed to update Book-Author relation.")
        }
    }
}