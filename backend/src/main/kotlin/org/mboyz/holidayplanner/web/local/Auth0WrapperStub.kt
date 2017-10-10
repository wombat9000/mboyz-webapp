package org.mboyz.holidayplanner.web.local

import com.auth0.Tokens
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.mboyz.holidayplanner.web.IAuth0Wrapper
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
@Profile("local")
class Auth0WrapperStub : IAuth0Wrapper {
    override fun getUserInfo(accessToken: String): MutableMap<String, Any> {
        return mutableMapOf(
                Pair("given_name", "Max"),
                Pair("family_name", "Mustermann"),
                Pair("picture", "http://localhost:8080/favicon.ico")
        )
    }

    override fun buildAuthorizeUrl(req: HttpServletRequest, redirectUri: String): String {
        return "auth0/authorize"
    }

    override fun handle(req: HttpServletRequest): Tokens {
        return Tokens("someAccessToken", provideSignedToken(),"","bearer",9000)
    }

    private fun provideSignedToken(): String {
        return JWT.create()
                .withSubject("facebook|123456")
                .withAudience("someAudience")
                .withIssuer("wombat9000.eu.auth0.com")
                .sign(Algorithm.HMAC256("someSecret"))
    }
}