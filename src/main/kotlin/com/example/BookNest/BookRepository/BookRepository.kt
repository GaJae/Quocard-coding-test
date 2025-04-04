// AuthorRepository.kt
package com.example.BookNest.repository

import com.example.db.Tables
import com.example.db.tables.records.AuthorsRecord
import com.example.db.tables.Authors
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.time.LocalDate

@Repository
class AuthorRepository(private val dsl: DSLContext) {

    fun findAll(): List<Authors> = dsl.selectFrom(Tables.AUTHORS).fetchInto(Authors::class.java)

    fun save(username: String, email: String): Authors? = dsl.insertInto(Tables.AUTHORS)
        .set(Tables.AUTHORS.NAME, username)
        .set(Tables.AUTHORS.BIRTH_DATE, LocalDate.now())
        .returning()
        .fetchOneInto(Authors::class.java)
}