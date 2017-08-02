package org.mboyz.holidayplanner.web

import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.web.PagesController
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup


class PagesControllerTest {

    lateinit var testee: PagesController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        initMocks(this)

        testee = PagesController()
        mockMvc = standaloneSetup(testee)
                .build()
    }

    @Test
    fun shouldShowHomePage() {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("home"))
    }
}