package org.mboyz.holidayplanner.integration

import org.flywaydb.core.Flyway
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = arrayOf("local"))
@AutoConfigureMockMvc
abstract class AbstractSpringTest {
    companion object {
        @BeforeClass
        @JvmStatic
        fun performMigrations() {
            val build = DataSourceBuilder
                    .create()
                    .username("sa")
                    .url("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                    .driverClassName("org.h2.Driver")
                    .build()

            val flyway = Flyway()
            flyway.dataSource = build
            flyway.clean()
            flyway.migrate()
        }
    }
}