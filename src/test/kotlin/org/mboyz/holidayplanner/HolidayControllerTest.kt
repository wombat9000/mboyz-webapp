package org.mboyz.holidayplanner

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.mock.web.MockHttpServletResponse
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

    val mapper = jacksonObjectMapper()

    @Before
    fun setUp() {
        holidayRepository.deleteAll()
    }

    @Test
    fun shouldCreateNewHolidays() {
        val response = mvc.perform(MockMvcRequestBuilders
                .post("/holiday/create")
                    .param("name", "someHoliday")
                    .param("location", "someLocation"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response

        val expectedHoliday = Holiday(1L, "someHoliday", "someLocation")

        val persistedHolidays: MutableIterable<Holiday> = holidayRepository.findAll()
        assertThat(persistedHolidays.first(), `is`(expectedHoliday))

        val holidayInResponse: Holiday = mapper.readValue(response.contentAsString)
        assertThat(holidayInResponse, `is`(expectedHoliday))
    }

    @Test
    fun shouldCreateHolidayWithUndefinedLocation() {
        val response = mvc.perform(MockMvcRequestBuilders
                .post("/holiday/create")
                    .param("name", "someHoliday"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response

        val expectedHoliday = Holiday(1L, "someHoliday")

        val persistedHolidays: MutableIterable<Holiday> = holidayRepository.findAll()
        assertThat(persistedHolidays.first().name, `is`(expectedHoliday.name))
        assertThat(persistedHolidays.first().location, `is`(expectedHoliday.location))

        val holidayInResponse: Holiday = mapper.readValue(response.contentAsString)
        assertThat(holidayInResponse.name, `is`(expectedHoliday.name))
        assertThat(holidayInResponse.location, `is`(expectedHoliday.location))
    }

    @Test
    fun shouldListExistingHolidays() {
        holidayRepository.save(listOf(Holiday(name = "someHoliday"), Holiday(name = "anotherHoliday", location = "someLocation")))

        val response: MockHttpServletResponse = mvc.perform(MockMvcRequestBuilders
                .get("/holiday/index"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn()
                .response

        val holidays: List<Holiday> = mapper.readValue(response.contentAsString)

        assertThat(holidays.size, `is`(2))
        assertThat(holidays.any { it.name == "someHoliday" }, `is`(true))
        assertThat(holidays.any { it.name == "anotherHoliday" && it.location == "someLocation"}, `is`(true))
    }
}
