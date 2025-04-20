package com.example.BookNest.repository

import com.example.db.Tables
import org.jooq.DSLContext
import org.springframework.stereotype.Repository

@Repository
class BookAuthorRepository(private val dsl: DSLContext) {

    fun addBookAuthor(bookId: Long, authorId: Long): Int {
        return dsl.insertInto(Tables.BOOK_AUTHOR)
            .set(Tables.BOOK_AUTHOR.BOOK_ID, bookId)
            .set(Tables.BOOK_AUTHOR.AUTHOR_ID, authorId)
            .execute()
    }

    fun removeBookAuthor(bookId: Long, authorId: Long): Int {
        return dsl.deleteFrom(Tables.BOOK_AUTHOR)
            .where(Tables.BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .and(Tables.BOOK_AUTHOR.AUTHOR_ID.eq(authorId))
            .execute()
    }

    fun findAuthorsByBookId(bookId: Long): List<Long> {
        return dsl.select(Tables.BOOK_AUTHOR.AUTHOR_ID)
            .from(Tables.BOOK_AUTHOR)
            .where(Tables.BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .fetchInto(Long::class.java)
    }

    fun findBooksByAuthorId(authorId: Long): List<Long> {
        return dsl.select(Tables.BOOK_AUTHOR.BOOK_ID)
            .from(Tables.BOOK_AUTHOR)
            .where(Tables.BOOK_AUTHOR.AUTHOR_ID.eq(authorId))
            .fetchInto(Long::class.java)
    }
    fun updateBookAuthor(bookId: Long, authorId: Long, newAuthorId: Long): Int {
        return dsl.update(Tables.BOOK_AUTHOR)
            .set(Tables.BOOK_AUTHOR.AUTHOR_ID, newAuthorId)
            .where(Tables.BOOK_AUTHOR.BOOK_ID.eq(bookId))
            .and(Tables.BOOK_AUTHOR.AUTHOR_ID.eq(authorId))
            .execute()
    }
}