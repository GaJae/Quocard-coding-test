import com.example.BookNest.BookNestApplication
import org.jooq.DSLContext
import org.jooq.Result
import org.jooq.impl.DSL
import org.jooq.Record3
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
            val result: Result<Record3<String, String, Int>> = DSL.using(connection)
                .select(
                    AUTHORS.NAME,
                    BOOKS.TITLE,
                    BOOKS.PRICE
                )
                .from(AUTHORS)
                .join(BOOKS)
                .on(AUTHORS.ID.eq(BOOKS.ID))
                .orderBy(BOOKS.ID.asc())
                .fetch()

            assertEquals(4, result.size)
            assertEquals(listOf(1, 2, 3, 4), result.getValues(BOOKS.ID))
        }
    }
}