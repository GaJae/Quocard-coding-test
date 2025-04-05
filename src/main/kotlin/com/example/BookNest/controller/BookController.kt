// BookController.kt
package com.example.BookNest.controller

import com.example.BookNest.service.BookService
import com.example.db.tables.records.BooksRecord
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/books")
class BookController(private val bookService: BookService) {

    @PostMapping
    fun createBook(@RequestBody book: BooksRecord): BooksRecord = bookService.saveBook(book)

    @GetMapping("/{id}")
    fun getBookById(@PathVariable id: Int): BooksRecord? = bookService.getBookById(id)

    @PutMapping("/{id}")
    fun updateBook(@PathVariable id: Int, @RequestBody book: BooksRecord): BooksRecord = bookService.updateBook(id, book)

    @DeleteMapping("/{id}")
    fun deleteBook(@PathVariable id: Int): Boolean = bookService.deleteBook(id)
}