package org.mboyz.holidayplanner.integration

import org.flywaydb.core.Flyway
import org.junit.After
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.participation.ParticipationRepository
import org.mboyz.holidayplanner.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@PropertySource("classpath:secret.properties", ignoreResourceNotFound = true)
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
                    .url("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE;MODE=PostgreSQL")
                    .driverClassName("org.h2.Driver")
                    .build()

            val flyway = Flyway()
            flyway.dataSource = build
            flyway.clean()
            flyway.migrate()
        }
    }

    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var holidayService: HolidayService
    @Autowired
    lateinit var participationRepository: ParticipationRepository

    @Before
    fun setup() {
        cleanDB()
    }

    @After
    fun tearDown() {
        cleanDB()
    }

    fun cleanDB() {
        holidayService.deleteAll()
        userService.deleteAll()
    }
}