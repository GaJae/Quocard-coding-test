package com.example.BookNest.service

import com.example.BookNest.repository.AuthorRepository
import com.example.db.tables.records.AuthorsRecord
import org.springframework.stereotype.Service

@Service
class AuthorService(private val authorRepository: AuthorRepository) {

    fun getAllAuthors(): List<AuthorsRecord> {
        return authorRepository.findAll()
    }

    fun getAuthorById(id: Int): AuthorsRecord? {
        return authorRepository.findById(id)
    }

    fun createAuthor(author: AuthorsRecord): AuthorsRecord {
        return authorRepository.save(author)
    }

    fun updateAuthor(id: Int, author: AuthorsRecord): AuthorsRecord {
        author.id = id
        return authorRepository.update(author)
    }

    fun deleteAuthor(id: Int): Boolean {
        return authorRepository.deleteById(id)
    }
}