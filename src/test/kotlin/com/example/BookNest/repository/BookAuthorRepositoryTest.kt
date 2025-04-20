package com.example.BookNest.repository

import com.example.db.tables.records.BookAuthorRecord
import org.jooq.DSLContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jooq.JooqTest
import org.springframework.context.annotation.Import

@JooqTest
@Import(BookAuthorRepository::class)
class BookAuthorRepositoryTest @Autowired constructor(
    private val bookAuthorRepository: BookAuthorRepository,
    private val dslContext: DSLContext
) {

    @BeforeEach
    fun setUp() {
        dslContext.execute("CREATE SCHEMA IF NOT EXISTS public")
        dslContext.execute("""
            CREATE TABLE IF NOT EXISTS public.books (
                id BIGINT PRIMARY KEY,
                title VARCHAR(255) NOT NULL,
                price INT NOT NULL,
                status VARCHAR(50) NOT NULL
            )
        """)
        dslContext.execute("""
            CREATE TABLE IF NOT EXISTS public.authors (
                id BIGINT PRIMARY KEY,
                name VARCHAR(255) NOT NULL,
                birth_date DATE
            )
        """)
        dslContext.execute("""
            CREATE TABLE IF NOT EXISTS public.book_author (
                book_id BIGINT NOT NULL,
                author_id BIGINT NOT NULL,
                PRIMARY KEY (book_id, author_id)
            )
        """)
        dslContext.execute("TRUNCATE TABLE public.book_author")
        dslContext.execute("TRUNCATE TABLE public.books")
        dslContext.execute("TRUNCATE TABLE public.authors")

        // 테스트 데이터 삽입
        dslContext.execute("INSERT INTO public.books (id, title, price, status) VALUES (1, 'Test Book', 1000, 'Available')")
        dslContext.execute("INSERT INTO public.authors (id, name, birth_date) VALUES (1, 'Test Author', '1990-01-01')")
    }

    @Test
    fun `addBookAuthor inserts a new record`() {
        val result = bookAuthorRepository.addBookAuthor(1L, 1L)

        assertEquals(1, result)
        val records = dslContext.selectFrom(com.example.db.Tables.BOOK_AUTHOR).fetch()
        assertEquals(1, records.size)
        assertEquals(1L, records[0].bookId)
        assertEquals(1L, records[0].authorId)
    }

    @Test
    fun `removeBookAuthor deletes the record`() {
        bookAuthorRepository.addBookAuthor(1L, 1L)

        val result = bookAuthorRepository.removeBookAuthor(1L, 1L)

        assertEquals(1, result)
        val records = dslContext.selectFrom(com.example.db.Tables.BOOK_AUTHOR).fetch()
        assertTrue(records.isEmpty())
    }

    @Test
    fun `findAuthorsByBookId returns author IDs`() {
        bookAuthorRepository.addBookAuthor(1L, 1L)

        val result = bookAuthorRepository.findAuthorsByBookId(1L)

        assertEquals(1, result.size)
        assertEquals(1L, result[0])
    }

    @Test
    fun `findBooksByAuthorId returns book IDs`() {
        bookAuthorRepository.addBookAuthor(1L, 1L)

        val result = bookAuthorRepository.findBooksByAuthorId(1L)

        assertEquals(1, result.size)
        assertEquals(1L, result[0])
    }

    @Test
    fun `updateBookAuthor updates the author ID for a given book`() {
        // 초기 데이터 삽입
        bookAuthorRepository.addBookAuthor(1L, 1L)

        // 업데이트 실행
        val result = bookAuthorRepository.updateBookAuthor(1L, 1L, 2L)

        // 결과 검증
        assertEquals(1, result) // 업데이트된 행의 수 확인
        val records = dslContext.selectFrom(com.example.db.Tables.BOOK_AUTHOR).fetch()
        assertEquals(1, records.size) // 레코드 수 확인
        assertEquals(1L, records[0].bookId) // 책 ID 확인
        assertEquals(2L, records[0].authorId) // 업데이트된 저자 ID 확인
    }
}