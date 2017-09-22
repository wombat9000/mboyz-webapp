package org.mboyz.holidayplanner.web

import com.auth0.AuthenticationController
import com.auth0.Tokens
import com.auth0.client.auth.AuthAPI
import com.auth0.json.auth.UserInfo
import com.auth0.net.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest

@Component
@Profile("prod")
class Auth0Wrapper
@Autowired
constructor(val auth0: AuthenticationController, val authAPI: AuthAPI): IAuth0Wrapper {
    override fun handle(req: HttpServletRequest): Tokens {
        return auth0.handle(req)
    }

    override fun buildAuthorizeUrl(req: HttpServletRequest, redirectUri: String): String {
        return auth0.buildAuthorizeUrl(req, redirectUri).build()
    }

    override fun getUserInfo(accessToken: String): MutableMap<String, Any> {
        val userInfo: Request<UserInfo> = authAPI.userInfo(accessToken)
        return userInfo.execute().values
    }
}