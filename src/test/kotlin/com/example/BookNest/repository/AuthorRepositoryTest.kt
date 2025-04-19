package com.example.BookNest.repository

import com.example.db.Tables
import com.example.db.tables.records.AuthorsRecord
import org.jooq.DSLContext
import org.jooq.SelectWhereStep
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.*
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class AuthorRepositoryTest {

    private lateinit var dsl: DSLContext
    private lateinit var authorRepository: AuthorRepository

    @BeforeEach
    fun setUp() {
        dsl = mock(DSLContext::class.java)
        dsl.settings().withRenderSchema(true)
        authorRepository = AuthorRepository(dsl)
    }

    @Test
    fun findAllReturnsListOfAuthors() {
        val authorsRecord = AuthorsRecord()
        val selectWhereStep = mock(SelectWhereStep::class.java) as SelectWhereStep<AuthorsRecord>
        `when`(dsl.selectFrom(Tables.AUTHORS)).thenReturn(selectWhereStep)
        `when`(selectWhereStep.fetchInto(AuthorsRecord::class.java)).thenReturn(listOf(authorsRecord))

        val result = authorRepository.findAll()

        assertEquals(1, result.size)
        assertEquals(authorsRecord, result[0])
    }

    @Test
    fun findAllReturnsEmptyListWhenNoAuthors() {
        val selectWhereStep = mock(SelectWhereStep::class.java) as SelectWhereStep<AuthorsRecord>
        `when`(dsl.selectFrom(Tables.AUTHORS)).thenReturn(selectWhereStep)
        `when`(selectWhereStep.fetchInto(AuthorsRecord::class.java)).thenReturn(emptyList())

        val result = authorRepository.findAll()

        assertTrue(result.isEmpty())
    }

//    @Test
//    fun saveInsertsAndReturnsAuthor() {
//        val username = "testUser"
//        val birthDate = LocalDate.now()
//        val authorsRecord = AuthorsRecord().apply {
//            name = username
//            this.birthDate = birthDate
//        }
//        val insertSetStep = mock(InsertSetStep::class.java) as InsertSetStep<AuthorsRecord>
//        `when`(dsl.insertInto(Tables.AUTHORS)).thenReturn(insertSetStep)
//        `when`(insertSetStep.set(authorsRecord)).thenReturn(insertSetStep)
//        `when`(insertSetStep.returning()).thenReturn(insertSetStep)
//        `when`(insertSetStep.fetchOne()).thenReturn(authorsRecord)
//
//        val result = authorRepository.save(authorsRecord)
//
//        assertNotNull(result)
//        assertEquals(authorsRecord, result)
//    }
//
//    @Test
//    fun saveReturnsNullWhenInsertFails() {
//        val username = "testUser"
//        val birthDate = LocalDate.now()
//        val authorsRecord = AuthorsRecord().apply {
//            name = username
//            this.birthDate = birthDate
//        }
//        val insertSetStep = mock(InsertSetMoreStep::class.java) as InsertSetMoreStep<AuthorsRecord>
//        `when`(dsl.insertInto(Tables.AUTHORS)).thenReturn(insertSetStep)
//        `when`(insertSetStep.set(authorsRecord)).thenReturn(insertSetStep)
//        `when`(insertSetStep.returning()).thenReturn(insertSetStep)
//        `when`(insertSetStep.fetchOne()).thenReturn(null)
//
//        val result = authorRepository.save(authorsRecord)
//
//        assertNull(result)
//    }
}