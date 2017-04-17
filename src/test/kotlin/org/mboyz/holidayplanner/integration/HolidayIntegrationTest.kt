package org.mboyz.holidayplanner.integration

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.time.LocalDate

class HolidayIntegrationTest : AbstractSpringTest() {
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
                    .param("location", "someLocation")
                    .param("startDate", "2007-12-12")
                    .param("endDate", "2007-12-13"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
                .andReturn()
                .response

        val expectedHoliday = Holiday(1L, "someHoliday", "someLocation", LocalDate.parse("2007-12-12"), LocalDate.parse("2007-12-13"))

        val persistedHolidays: MutableIterable<Holiday> = holidayRepository.findAll()
        assertThat(persistedHolidays.first(), `is`(expectedHoliday))

        val holidayInResponse: Holiday = mapper.readValue(response.contentAsString)
        assertThat(holidayInResponse, `is`(expectedHoliday))
    }

    @Test
    fun shouldCreateHolidayWithUndefinedOptionalFields() {
        val response = mvc.perform(MockMvcRequestBuilders
                .post("/holiday/create")
                    .param("name", "someHoliday"))
                .andExpect(MockMvcResultMatchers.status().isCreated)
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
    fun shouldNotCreateHolidayIfDatesAreInvalid() {
        mvc.perform(MockMvcRequestBuilders
                .post("/holiday/create")
                .param("name", "someHoliday")
                .param("location", "someLocation")
                .param("startDate", "2007-12-12")
                .param("endDate", "2007-12-11"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest)

        val persistedHolidays = holidayRepository.findAll().toList()
        assertTrue(persistedHolidays.isEmpty())
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
