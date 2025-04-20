package com.example.BookNest.repository

        import com.example.db.Tables
        import com.example.db.tables.records.AuthorsRecord
        import org.jooq.DSLContext
        import org.springframework.stereotype.Repository

        @Repository
        class AuthorRepository(private val dsl: DSLContext) {

            fun findAll(): List<AuthorsRecord> {
                return dsl.selectFrom(Tables.AUTHORS)
                    .fetchInto(AuthorsRecord::class.java)
            }

            fun findById(id: Int): AuthorsRecord? {
                return dsl.selectFrom(Tables.AUTHORS)
                    .where(Tables.AUTHORS.ID.eq(id))
                    .fetchOneInto(AuthorsRecord::class.java)
            }

            fun save(author: AuthorsRecord): AuthorsRecord {
                return dsl.insertInto(Tables.AUTHORS)
                    .set(author)
                    .returning()
                    .fetchOne() ?: throw IllegalStateException("Insert failed")
            }

            fun update(author: AuthorsRecord): AuthorsRecord {
                return dsl.update(Tables.AUTHORS)
                    .set(author)
                    .where(Tables.AUTHORS.ID.eq(author.id))
                    .returning()
                    .fetchOne() ?: throw IllegalStateException("Update failed")
            }

            fun deleteById(id: Int): Boolean {
                return dsl.deleteFrom(Tables.AUTHORS)
                    .where(Tables.AUTHORS.ID.eq(id))
                    .execute() > 0
            }
        }