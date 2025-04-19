package com.example.BookNest.repository

import com.example.db.tables.records.AuthorsRecord
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import
import java.time.LocalDate

@JooqTest
@Import(AuthorRepository::class)
class AuthorRepositoryTest @Autowired constructor(
    private val authorRepository: AuthorRepository,
    private val dslContext: DSLContext
) {

    @BeforeEach
    fun setUp() {
        dslContext.execute("CREATE SCHEMA IF NOT EXISTS public")
        dslContext.execute("""
            CREATE TABLE IF NOT EXISTS public.authors (
                id SERIAL PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                birth_date DATE
            )
        """)
        dslContext.execute("TRUNCATE TABLE public.authors")
    }

    @Test
    fun `findAll returns all authors`() {
        val author1 = AuthorsRecord().apply {
            name = "Author One"
            birthDate = LocalDate.parse("1980-01-01")
        }
        val author2 = AuthorsRecord().apply {
            name = "Author Two"
            birthDate = LocalDate.parse("1990-01-01")
        }
        authorRepository.save(author1)
        authorRepository.save(author2)

        val authors = authorRepository.findAll()

        assertEquals(2, authors.size)
        assertEquals("Author One", authors[0].name)
        assertEquals("Author Two", authors[1].name)
    }

    @Test
    fun `findById returns the correct author`() {
        val author = AuthorsRecord().apply {
            name = "Author Test"
            birthDate = LocalDate.parse("1985-05-15")
        }
        val savedAuthor = authorRepository.save(author)

        val foundAuthor = authorRepository.findById(savedAuthor.id)

        assertNotNull(foundAuthor)
        assertEquals(savedAuthor.name, foundAuthor?.name)
    }

    @Test
    fun `save inserts a new author`() {
        val author = AuthorsRecord().apply {
            name = "New Author"
            birthDate = LocalDate.parse("1975-03-10")
        }

        val savedAuthor = authorRepository.save(author)

        assertNotNull(savedAuthor)
        assertEquals(author.name, savedAuthor.name)
    }

    @Test
    fun `update modifies an existing author`() {
        val author = AuthorsRecord().apply {
            name = "Old Name"
            birthDate = LocalDate.parse("1970-01-01")
        }
        val savedAuthor = authorRepository.save(author)

        savedAuthor.name = "Updated Name"
        val updatedAuthor = authorRepository.update(savedAuthor)

        assertEquals("Updated Name", updatedAuthor.name)
    }

    @Test
    fun `deleteById removes the author`() {
        val author = AuthorsRecord().apply {
            name = "Author To Delete"
            birthDate = LocalDate.parse("1960-12-12")
        }
        val savedAuthor = authorRepository.save(author)

        val isDeleted = authorRepository.deleteById(savedAuthor.id)

        assertTrue(isDeleted)
        assertNull(authorRepository.findById(savedAuthor.id))
    }
}