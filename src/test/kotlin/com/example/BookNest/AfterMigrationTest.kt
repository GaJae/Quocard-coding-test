import com.example.BookNest.BookNestApplication
import org.jooq.DSLContext
import org.jooq.Result
import org.jooq.impl.DSL
import org.jooq.Record4
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import java.sql.Connection
import java.sql.DriverManager
import com.example.db.Tables.AUTHORS
import com.example.db.Tables.BOOKS

@SpringBootTest(classes = [BookNestApplication::class])
@ActiveProfiles("test")
class AfterMigrationTest {

    @Autowired
    private lateinit var dsl: DSLContext

    @Test
    fun testQueryingAfterMigration() {
        val url = "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1"
        val user = "sa"
        val password = ""

        DriverManager.getConnection(url, user, password).use { connection ->
            val dslContext = DSL.using(connection)

            // 테이블 생성
            dslContext.execute("""
                CREATE TABLE PUBLIC.AUTHORS (
                    ID SERIAL PRIMARY KEY,
                    NAME VARCHAR(255) NOT NULL,
                    BIRTH_DATE DATE NOT NULL
                )
            """)
            dslContext.execute("""
                CREATE TABLE PUBLIC.BOOKS (
                    ID SERIAL PRIMARY KEY,
                    TITLE VARCHAR(255) NOT NULL,
                    PRICE INT NOT NULL CHECK (PRICE >= 0),
                    STATUS VARCHAR(50) NOT NULL
                )
            """)
            dslContext.execute("""
                CREATE TABLE PUBLIC.BOOK_AUTHOR (
                    BOOK_ID BIGINT NOT NULL,
                    AUTHOR_ID BIGINT NOT NULL,
                    PRIMARY KEY (BOOK_ID, AUTHOR_ID),
                    FOREIGN KEY (BOOK_ID) REFERENCES PUBLIC.BOOKS(ID),
                    FOREIGN KEY (AUTHOR_ID) REFERENCES PUBLIC.AUTHORS(ID)
                )
            """)

            // 데이터 삽입
            dslContext.execute("INSERT INTO PUBLIC.AUTHORS (ID, NAME, BIRTH_DATE) VALUES (1, 'Author 1', '1970-01-01'), (2, 'Author 2', '1980-01-01'), (3, 'Author 3', '1990-01-01'), (4, 'Author 4', '2000-01-01')")
            dslContext.execute("INSERT INTO PUBLIC.BOOKS (ID, TITLE, PRICE, STATUS) VALUES (1, 'Book 1', 100, 'Available'), (2, 'Book 2', 200, 'Available'), (3, 'Book 3', 300, 'Available'), (4, 'Book 4', 400, 'Available')")
            dslContext.execute("INSERT INTO PUBLIC.BOOK_AUTHOR (BOOK_ID, AUTHOR_ID) VALUES (1, 1), (2, 2), (3, 3), (4, 4)")

            val result: Result<Record4<Any, Any, Any, Any>> = dslContext
                .select(
                    DSL.field("PUBLIC.AUTHORS.NAME"),
                    DSL.field("PUBLIC.BOOKS.TITLE"),
                    DSL.field("PUBLIC.BOOKS.PRICE"),
                    DSL.field("PUBLIC.BOOKS.ID")
                )
                .from(DSL.table("PUBLIC.AUTHORS"))
                .join(DSL.table("PUBLIC.BOOKS"))
                .on(DSL.field("PUBLIC.AUTHORS.ID").eq(DSL.field("PUBLIC.BOOKS.ID")))
                .orderBy(DSL.field("PUBLIC.BOOKS.ID").asc())
                .fetch()

            assertEquals(4, result.size)
            assertEquals(listOf(1, 2, 3, 4), result.getValues(DSL.field("PUBLIC.BOOKS.ID", Int::class.java)))
        }
    }
}