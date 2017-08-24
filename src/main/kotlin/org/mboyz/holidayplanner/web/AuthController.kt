package org.mboyz.holidayplanner.web

import com.auth0.IdentityVerificationException
import com.auth0.Tokens
import com.auth0.jwt.JWT
import org.mboyz.holidayplanner.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest

@Controller
class AuthController
@Autowired
constructor(val auth0: Auth0Wrapper, val userService: UserService) {

    companion object {
        const private val HOME: String = "/"
        const private val LOGIN: String = "/login"
        const private val CALLBACK: String = "/callback"
    }

    @RequestMapping(value = LOGIN, method = arrayOf(RequestMethod.GET))
    fun login(req: HttpServletRequest): String {
        val authorizeUrl = auth0.buildAuthorizeUrl(req, req.getRedirectUrl())
        return "redirect:" + authorizeUrl
    }

    @RequestMapping(value = CALLBACK, method = arrayOf(RequestMethod.GET))
    fun getCallback(req: HttpServletRequest): String {
        return try {
            val tokens: Tokens = auth0.handle(req)
            val tokenAuth = TokenAuthentication(JWT.decode(tokens.idToken))
            SecurityContextHolder.getContext().authentication = tokenAuth
            userService.createOrUpdate(tokenAuth.name, tokens.accessToken)
            "redirect:$HOME"
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            "redirect:$LOGIN"
        } catch (e: IdentityVerificationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            "redirect:$LOGIN"
        }
    }

    private fun HttpServletRequest.getRedirectUrl(): String {
        var redirectUri = this.scheme + "://" + this.serverName

        // Localhost requires port in URL because it does not run on 8080 instead of default 80 / 443 which are omitted
        if (this.serverPort == 8080) {
            redirectUri += ":" + this.serverPort
        }

        redirectUri += AuthController.CALLBACK
        return redirectUri
    }
}


