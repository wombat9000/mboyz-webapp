package org.mboyz.holidayplanner.integration

import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.persistence.ParticipationRepository
import org.mboyz.holidayplanner.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import javax.sql.DataSource

@RunWith(SpringRunner::class)
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