package org.mboyz.holidayplanner.holiday

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.security.Principal

class HolidayActionsControllerTest {

    lateinit var testee: HolidayActionsController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testee = HolidayActionsController()
        mockMvc = MockMvcBuilders.standaloneSetup(testee).build()
    }

    @Test
    fun shouldRedirectToHolidayPageAfterSuccessfulParticipation() {
        val somePrincipal = Principal{"test"}
        mockMvc.perform(MockMvcRequestBuilders.get("/holiday/1/participate").principal(somePrincipal))
                .andExpect(MockMvcResultMatchers.view().name("redirect:/holiday/1"))
                .andExpect(MockMvcResultMatchers.status().isFound)
    }
}