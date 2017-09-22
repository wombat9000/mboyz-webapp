package org.mboyz.holidayplanner.web

import com.auth0.Tokens
import javax.servlet.http.HttpServletRequest


interface IAuth0Wrapper {
    fun handle(req: HttpServletRequest): Tokens
    fun buildAuthorizeUrl(req: HttpServletRequest, redirectUri: String): String
    fun getUserInfo(accessToken: String): MutableMap<String, Any>
}