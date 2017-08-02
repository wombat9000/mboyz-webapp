package org.mboyz.holidayplanner.web

import org.junit.Before
import org.junit.Test
import org.mockito.MockitoAnnotations
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.servlet.view.InternalResourceViewResolver

class ErrorPagesControllerTest {
    lateinit var testee: ErrorPagesController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        val viewResolver = InternalResourceViewResolver()
        viewResolver.setSuffix(".html")

        testee = ErrorPagesController()
        mockMvc = MockMvcBuilders.standaloneSetup(testee)
                .setViewResolvers(viewResolver)
                .build()
    }

    @Test
    fun shouldShowErrorPage() {
        mockMvc.perform(MockMvcRequestBuilders.get("/error"))
                .andExpect(MockMvcResultMatchers.status().isOk)
                .andExpect(MockMvcResultMatchers.view().name("error"))
    }
}