package org.mboyz.holidayplanner.web

import com.auth0.client.auth.AuthAPI
import com.auth0.json.auth.UserInfo
import com.auth0.net.Request
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class Auth0Client
@Autowired
constructor(val authAPI: AuthAPI) {

    fun getUserInfo(accessToken: String): MutableMap<String, Any> {
        val userInfo: Request<UserInfo> = authAPI.userInfo(accessToken)
        return userInfo.execute().values
    }
}