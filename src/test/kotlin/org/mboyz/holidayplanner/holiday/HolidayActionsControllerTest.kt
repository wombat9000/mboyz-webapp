package org.mboyz.holidayplanner.holiday

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import java.security.Principal

class HolidayActionsControllerTest {

    lateinit var testee: HolidayActionsController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayActionsController()
        mockMvc = standaloneSetup(testee).build()
    }

    @Test
    fun shouldRedirectToHolidayPageAfterSuccessfulParticipation() {
        val somePrincipal = Principal{"test"}
        mockMvc.perform(get("/holiday/1/participate").principal(somePrincipal))
                .andExpect(view().name("redirect:/holiday/1"))
                .andExpect(status().isFound)
    }
}