package org.mboyz.holidayplanner.integration

import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.web.PageController
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class PageControllerTest {

    lateinit var testee: PageController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        testee = PageController()
        mockMvc = MockMvcBuilders.standaloneSetup(testee).build()
    }
    @Test
    fun shouldShowHomePage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("home"))
    }
}