package com.example.BookNest.repository

    import com.example.BookNest.dto.BookDTO
    import com.example.db.tables.records.BooksRecord
    import com.example.db.tables.Books.BOOKS
    import org.jooq.DSLContext
    import org.springframework.stereotype.Repository

    @Repository
    class BookRepository(private val dsl: DSLContext) {

        fun save(book: BookDTO): BooksRecord {
            val record = dsl.newRecord(BOOKS).apply {
                this.title = book.title
                this.price = book.price
                this.status = book.status
            }

            return dsl.insertInto(BOOKS)
                .set(record)
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

        fun update(id: Int, book: BookDTO): BooksRecord {
            val record = dsl.newRecord(BOOKS).apply {
                this.title = book.title
                this.price = book.price
                this.status = book.status
            }

            return dsl.update(BOOKS)
                .set(record)
                .where(BOOKS.ID.eq(id))
                .returning()
                .fetchOne() ?: throw IllegalStateException("Update failed")
        }

    fun deleteById(id: Int): Boolean {
        return dsl.deleteFrom(BOOKS)
            .where(BOOKS.ID.eq(id))
            .execute() > 0
    }
    }