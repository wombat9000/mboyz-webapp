package org.mboyz.holidayplanner.holiday

import org.hamcrest.CoreMatchers.hasItem
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import org.springframework.util.MultiValueMap
import java.time.LocalDate

class HolidayPagesControllerTest {
    lateinit var testee: HolidayPagesController
    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var holidayService: HolidayService

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayPagesController(holidayService)
        mockMvc = standaloneSetup(testee).build()
    }

    @Test
    fun shouldProvideIndexView() {
        val expectedHolidays = listOf(Holiday(0L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03")))
        `when`(holidayService.findAll()).thenReturn(expectedHolidays)

        mockMvc.perform(get("/holiday"))
                .andExpect(view().name("holiday/index"))
                .andExpect(model().attribute("allHolidays", expectedHolidays))
                .andExpect(status().isOk)
    }

    @Test
    fun shouldProvideDetailView() {
        val expectedHoliday = Holiday(1L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03"))
        `when`(holidayService.findOne(1)).thenReturn(expectedHoliday)

        mockMvc.perform(get("/holiday/1"))
                .andExpect(view().name("holiday/detail"))
                .andExpect(model().attribute("holiday", expectedHoliday))
                .andExpect(status().isOk)

        verify(holidayService, times(1)).findOne(1)
    }

    @Test
    fun shouldHandleCreateHoliday() {
        mockMvc.perform(get("/holiday/create"))
                .andExpect(view().name("holiday/form"))
                .andExpect(model().attribute("holiday", instanceOf<Holiday>(Holiday::class.java)))
                .andExpect(status().isOk)
    }

    @Test
    fun shouldHandleSaveHoliday() {
        val someHoliday = Holiday(name = "someName", location = "someLocation")
        val somePersistedHoliday = Holiday(id = 1, name = "someName", location = "someLocation")

        `when`(holidayService.save(someHoliday)).thenReturn(somePersistedHoliday)

        mockMvc.perform(post("/holiday/create")
                .param("name", someHoliday.name)
                .param("location", someHoliday.location))
                .andExpect(view().name("redirect:/holiday/1"))
                .andExpect(status().isCreated)
    }
}