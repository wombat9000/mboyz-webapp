package org.mboyz.holidayplanner.integration

import com.auth0.Tokens
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.flywaydb.core.Flyway
import org.junit.Before
import org.junit.BeforeClass
import org.junit.runner.RunWith
import org.mboyz.holidayplanner.any
import org.mboyz.holidayplanner.web.Auth0Client
import org.mboyz.holidayplanner.web.Auth0Wrapper
import org.mockito.BDDMockito
import org.mockito.BDDMockito.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.PropertySource
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import java.util.*

@RunWith(SpringRunner::class)
@PropertySource("classpath:secret.properties", ignoreResourceNotFound = true)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(profiles = arrayOf("local"))
@AutoConfigureMockMvc
abstract class AbstractSpringTest {

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String

    @MockBean
    lateinit var auth0Mock: Auth0Wrapper
    @MockBean
    lateinit var auth0Client: Auth0Client

    @Before fun setup() {
        val userInfo = mutableMapOf<String, Any>()
        userInfo.put("given_name", "Bastian")
        userInfo.put("family_name", "Stein")
        userInfo.put("picture", "testImageUrl")

        given(auth0Mock.buildAuthorizeUrl(any(), any())).willReturn("/auth0Test")
        given(auth0Mock.handle(any())).willReturn(Tokens("someAccessToken", generateSignedToken(),"", "bearer", 9000))
        given(auth0Client.getUserInfo("someAccessToken")).willReturn(userInfo)
    }

    companion object {
        @BeforeClass
        @JvmStatic
        fun performMigrations() {
            val build = DataSourceBuilder
                    .create()
                    .username("sa")
                    .url("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                    .driverClassName("org.h2.Driver")
                    .build()

            val flyway = Flyway()
            flyway.dataSource = build
            flyway.clean()
            flyway.migrate()
        }
    }

    fun generateSignedToken(): String {
        // TODO
        // explore how to avoid using the real secret
        return JWT.create()
                .withSubject("facebook|someFacebookUser")
                .withAudience("someAudience")
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(Algorithm.HMAC256(AUTH0_SECRET))
    }
}