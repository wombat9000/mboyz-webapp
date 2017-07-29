package org.mboyz.holidayplanner.web

import com.auth0.AuthenticationController
import com.auth0.Tokens
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
class Auth0Wrapper(@Autowired val auth0: AuthenticationController) {
    fun handle(req: HttpServletRequest): Tokens {
        return auth0.handle(req)
    }

    fun buildAuthorizeUrl(req: HttpServletRequest?, redirectUri: String?): String {
        return auth0.buildAuthorizeUrl(req, redirectUri).build()
    }
}