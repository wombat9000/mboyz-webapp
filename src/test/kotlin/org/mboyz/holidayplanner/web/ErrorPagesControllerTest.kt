package org.mboyz.holidayplanner.web

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.boot.autoconfigure.web.ErrorAttributes
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view
import org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup
import org.springframework.web.servlet.view.InternalResourceViewResolver

class ErrorPagesControllerTest {
    companion object {
        val NO_DEBUG = false
    }

    @Mock
    lateinit var errorAttributes: ErrorAttributes
    lateinit var testee: ErrorPagesController
    lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        initMocks(this)
        val viewResolver = InternalResourceViewResolver()
        viewResolver.setSuffix(".html")

        testee = ErrorPagesController(NO_DEBUG, errorAttributes)
        mockMvc = standaloneSetup(testee)
                .setViewResolvers(viewResolver)
                .build()
    }

    @Test
    fun shouldShowErrorPage() {
        mockMvc.perform(get("/error"))
                .andExpect(status().isOk)
                .andExpect(view().name("error/generic"))
    }
}