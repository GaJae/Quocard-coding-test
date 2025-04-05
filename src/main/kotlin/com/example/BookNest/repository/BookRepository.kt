package com.example.BookNest.repository

    import com.example.db.tables.records.BooksRecord
    import com.example.db.tables.Books.BOOKS
    import org.jooq.DSLContext
    import org.springframework.stereotype.Repository

    @Repository
    class BookRepository(private val dsl: DSLContext) {

        fun save(book: BooksRecord): BooksRecord {
        return dsl.insertInto(BOOKS)
                .set(book)
                .returning()
            .fetchOne() ?: throw IllegalStateException("Insert failed")
    }

    fun findById(id: Int): BooksRecord? {
        return dsl.selectFrom(BOOKS)
            .where(BOOKS.ID.eq(id))
            .fetchOneInto(BooksRecord::class.java)
        }

    fun findAll(): List<BooksRecord> {
            return dsl.selectFrom(BOOKS)
                .fetchInto(BooksRecord::class.java)
        }

    fun update(book: BooksRecord): BooksRecord {
        return dsl.update(BOOKS)
            .set(book)
            .where(BOOKS.ID.eq(book.id))
            .returning()
            .fetchOne() ?: throw IllegalStateException("Update failed")
    }

    fun deleteById(id: Int): Boolean {
        return dsl.deleteFrom(BOOKS)
            .where(BOOKS.ID.eq(id))
            .execute() > 0
    }
    }