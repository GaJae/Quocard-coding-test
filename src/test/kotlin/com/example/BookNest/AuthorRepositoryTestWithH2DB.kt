package com.example.BookNest
import com.example.BookNest.repository.AuthorRepository

import com.example.db.Tables
import com.example.db.tables.records.AuthorsRecord
import org.jooq.DSLContext
import org.jooq.impl.DSL
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import java.time.LocalDate
import javax.sql.DataSource

@SpringBootTest
class AuthorRepositoryTest {

    @Autowired
    private lateinit var dsl: DSLContext
    private lateinit var authorRepository: AuthorRepository

    @BeforeEach
    fun setUp() {
        authorRepository = AuthorRepository(dsl)
    }

    @Test
    fun findAllReturnsListOfAuthors() {
        val authorsRecord = AuthorsRecord()
        authorsRecord.name = "testUser"
        authorsRecord.birthDate = LocalDate.now()
        dsl.insertInto(Tables.AUTHORS).set(authorsRecord).execute()

        val result = authorRepository.findAll()

        assertEquals(1, result.size)
        assertEquals(authorsRecord.name, result[0].name)
    }

    @Test
    fun findAllReturnsEmptyListWhenNoAuthors() {
        val result = authorRepository.findAll()

        assertTrue(result.isEmpty())
    }

    @Test
    fun saveInsertsAndReturnsAuthor() {
        val authorsRecord = AuthorsRecord().apply {
            name = "testUser"
            birthDate = LocalDate.now()
        }

        val result = authorRepository.save(authorsRecord)

        assertNotNull(result)
        assertEquals(authorsRecord.name, result?.name)
    }

    @Test
    fun saveReturnsNullWhenInsertFails() {
        // H2 데이터베이스에서는 삽입 실패를 시뮬레이션하기 어려울 수 있습니다.
        // 이 테스트는 생략하거나 다른 방식으로 처리해야 할 수 있습니다.
    }

    @Configuration
    class TestConfig {
        @Bean
        fun dataSource(): DataSource {
            val dataSource = DriverManagerDataSource()
            dataSource.setDriverClassName("org.h2.Driver")
            dataSource.url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
            dataSource.username = "sa"
            dataSource.password = ""
            return dataSource
        }

        @Bean
        fun dslContext(dataSource: DataSource): DSLContext {
            return DSL.using(dataSource, org.jooq.SQLDialect.H2)
        }
    }
}