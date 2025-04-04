// BookController.kt
package com.example.BookNest.BookController

import com.example.BookNest.BookService.BookService
import com.example.db.tables.records.BooksRecord
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @PostMapping
    fun createBook(@RequestBody book: BooksRecord): BooksRecord = bookService.saveBook(book)

    @GetMapping("/author/{authorId}")
    fun getBooksByAuthor(@PathVariable authorId: Long): List<BooksRecord> = bookService.getBooksByAuthor(authorId)
}