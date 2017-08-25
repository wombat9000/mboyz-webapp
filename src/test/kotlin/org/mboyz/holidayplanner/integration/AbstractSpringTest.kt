package org.mboyz.holidayplanner.integration

import org.flywaydb.core.Flyway
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.participation.ParticipationRepository
import org.mboyz.holidayplanner.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.junit4.SpringRunner
import javax.annotation.PostConstruct
import javax.sql.DataSource

@RunWith(SpringRunner::class)
@PropertySource("classpath:secret.properties", ignoreResourceNotFound = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
abstract class AbstractSpringTest {

    @Autowired
    lateinit var dataSource: DataSource
    @Autowired
    lateinit var userService: UserService
    @Autowired
    lateinit var holidayService: HolidayService
    @Autowired
    lateinit var participationRepository: ParticipationRepository

    @PostConstruct
    fun performMigrations() {
        val flyway = Flyway()
        flyway.dataSource = dataSource
        flyway.clean()
        flyway.migrate()
    }

    @Before
    open fun setup() {
        cleanDB()
    }

    @After
    open fun tearDown() {
        cleanDB()
    }

    fun cleanDB() {
        holidayService.deleteAll()
        userService.deleteAll()
    }
}