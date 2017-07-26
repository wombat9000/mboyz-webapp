package org.mboyz.holidayplanner.web

import com.auth0.AuthenticationController
import com.auth0.IdentityVerificationException
import com.auth0.example.security.TokenAuthentication
import com.auth0.jwt.JWT
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
class CallbackController(@Autowired val controller: AuthenticationController) {

    private val redirectOnFail: String = "/login"
    private val redirectOnSuccess: String = "/portal/home"

    @RequestMapping(value = "/callback", method = arrayOf(RequestMethod.GET))
    @Throws(ServletException::class, IOException::class)
    fun getCallback(req: HttpServletRequest, res: HttpServletResponse) {
        handle(req, res)
    }

    @RequestMapping(value = "/callback", method = arrayOf(RequestMethod.POST), consumes = arrayOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE))
    @Throws(ServletException::class, IOException::class)
    fun postCallback(req: HttpServletRequest, res: HttpServletResponse) {
        handle(req, res)
    }

    @Throws(IOException::class)
    private fun handle(req: HttpServletRequest, res: HttpServletResponse) {
        try {
            val tokens = controller.handle(req)
            val tokenAuth = TokenAuthentication(JWT.decode(tokens.idToken))
            SecurityContextHolder.getContext().authentication = tokenAuth
            res.sendRedirect(redirectOnSuccess)
        } catch (e: AuthenticationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            res.sendRedirect(redirectOnFail)
        } catch (e: IdentityVerificationException) {
            e.printStackTrace()
            SecurityContextHolder.clearContext()
            res.sendRedirect(redirectOnFail)
        }

    }
}
