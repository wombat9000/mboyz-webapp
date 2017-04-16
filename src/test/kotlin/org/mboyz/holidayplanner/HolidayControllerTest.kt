package org.mboyz.holidayplanner

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles(profiles = arrayOf("local"))
@AutoConfigureMockMvc
class HolidayControllerTest {
    @Autowired
    lateinit var mvc: MockMvc
    @Autowired
    lateinit var holidayRepository: HolidayRepository

    @Test
    fun shouldCreateNewHolidays() {
        mvc.perform(MockMvcRequestBuilders
                .post("/holiday/create").param("name", "someHoliday"))
                .andExpect(MockMvcResultMatchers.status().isOk)

        val actualHolidays: MutableIterable<Holiday> = holidayRepository.findAll()

        assertThat(actualHolidays.first().name, `is`("someHoliday"))
    }
}
