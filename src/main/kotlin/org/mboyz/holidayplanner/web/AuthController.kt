package org.mboyz.holidayplanner.web

import com.auth0.IdentityVerificationException
import com.auth0.jwt.JWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@Controller
class AuthController(@Autowired val auth0: Auth0Wrapper) {

    companion object {
        const private val HOME: String = "/"
        const private val LOGIN: String = "/login"
        const private val CALLBACK: String = "/callback"
    }

    @RequestMapping(value = LOGIN, method = arrayOf(RequestMethod.GET))
    fun login(req: HttpServletRequest): String {
        val redirectUri = req.scheme + "://" + req.serverName + ":" + req.serverPort + CALLBACK
        val authorizeUrl = auth0.buildAuthorizeUrl(req, redirectUri)
        return "redirect:" + authorizeUrl
    }

    @RequestMapping(value = CALLBACK, method = arrayOf(RequestMethod.GET))
    fun getCallback(req: HttpServletRequest): String {
        try {
            val tokens = auth0.handle(req)
            val tokenAuth = TokenAuthentication(JWT.decode(tokens.idToken))
            SecurityContextHolder.getContext().authentication = tokenAuth
            return "redirect:$HOME"
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            return "redirect:$LOGIN"
        } catch (e: IdentityVerificationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            return "redirect:$LOGIN"
        }
    }
}