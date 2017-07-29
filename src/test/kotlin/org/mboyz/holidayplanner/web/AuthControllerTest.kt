package org.mboyz.holidayplanner.web

import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.any
import org.mockito.MockitoAnnotations.initMocks
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders

class AuthControllerTest {

    lateinit var testee: AuthController

    lateinit var mockMvc: MockMvc


    @Mock
    lateinit var auth0Mock: Auth0Wrapper

    @Before
    fun setUp() {
    	initMocks(this)
    	testee = AuthController(auth0Mock)
        mockMvc = MockMvcBuilders.standaloneSetup(testee).build()
    }

    @Test
    fun shouldRedirectLoginToAuth0() {
        val redirectUrl = "someUrl"
        `when`(auth0Mock.buildAuthorizeUrl(any(), any())).thenReturn(redirectUrl)

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(redirectUrl))
                .andExpect(MockMvcResultMatchers.status().isFound)
    }
}