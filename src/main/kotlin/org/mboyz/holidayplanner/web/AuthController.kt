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
import javax.servlet.http.HttpServletResponse

@Controller
class AuthController(@Autowired val auth0: Auth0Wrapper,
                     @Autowired val userService: UserService) {

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
    fun getCallback(req: HttpServletRequest, res: HttpServletResponse) {
        try {
            val tokens: Tokens = auth0.handle(req)
            val tokenAuth: TokenAuthentication = TokenAuthentication(JWT.decode(tokens.idToken))
            SecurityContextHolder.getContext().authentication = tokenAuth
            userService.createOrUpdate(tokenAuth.name, tokens.accessToken)
            res.sendRedirect(HOME)
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            res.sendRedirect(LOGIN)
        } catch (e: IdentityVerificationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            res.sendRedirect(LOGIN)
        }
    }
}