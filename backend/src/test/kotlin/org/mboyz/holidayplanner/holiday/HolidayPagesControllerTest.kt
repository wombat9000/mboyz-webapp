package org.mboyz.holidayplanner.holiday

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.holiday.persistence.ParticipationEntity
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.mboyz.holidayplanner.user.UserService
import org.mboyz.holidayplanner.web.TokenAuthentication
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.*
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import java.time.LocalDate

class HolidayPagesControllerTest {
    lateinit var testee: HolidayPagesController
    lateinit var mockMvc: MockMvc

    @Mock
    lateinit var holidayService: HolidayService
    @Mock
    lateinit var userService: UserService

    private val token: String = JWT.create()
            .withSubject("someFbId")
            .withAudience("someAudience")
            .withIssuer("https://wombat9000.eu.auth0.com/")
            .sign(Algorithm.HMAC256("someSecret"))!!
    private val withTokenAuth: TokenAuthentication = TokenAuthentication(JWT.decode(token))

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayPagesController(holidayService, userService)
        mockMvc = standaloneSetup(testee).build()
    }

    @Test
    fun shouldProvideIndexView() {
        val expectedHolidays = listOf(HolidayEntity(0L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03")))
        given(holidayService.findAll()).willReturn(expectedHolidays)

        mockMvc.perform(get("/holiday"))
                .andExpect(view().name("holiday/index"))
                .andExpect(model().attribute("allHolidays", expectedHolidays))
                .andExpect(status().isOk)
    }

    @Test
    fun shouldProvideDetailView() {
        val expectedHoliday = HolidayEntity(1L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03"))
        given(holidayService.findOne(1)).willReturn(expectedHoliday)

        mockMvc.perform(get("/holiday/1").principal(withTokenAuth))
                .andExpect(view().name("holiday/detail"))
                .andExpect(model().attribute("holiday", expectedHoliday.asHolidayDetail()))
                .andExpect(model().attribute("isParticipating", false))
                .andExpect(status().isOk)

        verify(holidayService, times(1)).findOne(1)
    }

    @Test
    fun shouldProvideDetailViewAndIndicateUserIsParticipating() {
        val expectedHoliday = HolidayEntity(1L, "someName", "someLocation", LocalDate.parse("1990-12-02"), LocalDate.parse("2100-12-03"))
        given(holidayService.findOne(1)).willReturn(expectedHoliday)
        given(userService.findByFbId("someFbId")).willReturn(UserEntity(participations = mutableSetOf(ParticipationEntity(holiday = expectedHoliday))))

        mockMvc.perform(get("/holiday/1").principal(withTokenAuth))
                .andExpect(view().name("holiday/detail"))
                .andExpect(model().attribute("holiday", expectedHoliday.asHolidayDetail()))
                .andExpect(model().attribute("isParticipating", true))
                .andExpect(status().isOk)

        verify(holidayService, times(1)).findOne(1)
    }

    @Test
    fun shouldHandleCreateHoliday() {
        mockMvc.perform(get("/holiday/create"))
                .andExpect(view().name("holiday/form"))
                .andExpect(model().attribute("holidayEntity", instanceOf<HolidayEntity>(HolidayEntity::class.java)))
                .andExpect(status().isOk)
    }

    @Test
    fun shouldHandleSaveHoliday() {
        val someHoliday = HolidayEntity(name = "someName", location = "someLocation")
        val somePersistedHoliday = HolidayEntity(id = 1, name = "someName", location = "someLocation")

        given(holidayService.save(someHoliday)).willReturn(somePersistedHoliday)

        mockMvc.perform(post("/holiday/create")
                .param("name", someHoliday.name)
                .param("location", someHoliday.location))
                .andExpect(view().name("redirect:/holiday/1"))
                .andExpect(status().isFound)
    }
}