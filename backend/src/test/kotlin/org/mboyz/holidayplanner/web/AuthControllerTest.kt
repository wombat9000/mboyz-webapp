package org.mboyz.holidayplanner.web

import com.auth0.Tokens
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.any
import org.mboyz.holidayplanner.user.User
import org.mboyz.holidayplanner.user.UserService
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
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
    @Mock
    lateinit var userServiceMock: UserService

    @Before
    fun setUp() {
    	initMocks(this)
    	testee = AuthController(auth0Mock, userServiceMock)
        mockMvc = MockMvcBuilders.standaloneSetup(testee).build()
    }

    @Test
    fun shouldRedirectLoginToAuth0() {
        val redirectUrl = "someUrl"
        given(auth0Mock.buildAuthorizeUrl(any(), any())).willReturn(redirectUrl)

        mockMvc.perform(MockMvcRequestBuilders.get("/login"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(redirectUrl))
                .andExpect(MockMvcResultMatchers.status().isFound)
    }

    @Test
    fun callbackShouldCreateUserInDb() {
        val home = "/"

        val accessToken = "someAccessToken"
        val token = Tokens(accessToken, generateToken(), "", "", 60L)
        given(auth0Mock.handle(any())).willReturn(token)
        given(userServiceMock.createOrUpdate("someSubject", accessToken)).willReturn(User())

        mockMvc.perform(MockMvcRequestBuilders.get("/callback"))
                .andExpect(MockMvcResultMatchers.redirectedUrl(home))
                .andExpect(MockMvcResultMatchers.status().isFound)

        verify(userServiceMock).createOrUpdate("someSubject", accessToken)
    }

    fun generateToken(): String {
        return JWT.create()
                .withSubject("someSubject")
                .withIssuer("someIssuer")
                .sign(Algorithm.HMAC256("secret"))
    }
}