package org.mboyz.holidayplanner.holiday

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.web.TokenAuthentication
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class HolidayActionsControllerTest {

    lateinit var testee: HolidayActionsController
    @Mock
    lateinit var holidayService: HolidayService
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayActionsController(holidayService)
        mockMvc = standaloneSetup(testee).build()
    }

    @Test
    fun shouldRedirectToHolidayPageAfterSuccessfulParticipation() {
        val token = JWT.create()
                .withSubject("someFbId")
                .withAudience("someAudience")
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(Algorithm.HMAC256("someSecret"))
        val tokenAuth = TokenAuthentication(JWT.decode(token))

//        given(holidayService.registerParticipation(1L, "someFbId")).willReturn()

        mockMvc.perform(get("/holiday/1/participate").principal(tokenAuth))
                .andExpect(view().name("redirect:/holiday/1"))
                .andExpect(status().isFound)

        verify(holidayService).registerParticipation(1L, "someFbId")
    }
}