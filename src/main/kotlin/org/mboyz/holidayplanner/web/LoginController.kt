package org.mboyz.holidayplanner.web

import com.auth0.AuthenticationController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import javax.servlet.http.HttpServletRequest


@Controller
class LoginController(@Autowired val authenticationController: AuthenticationController) {

    @RequestMapping(value = "/login", method = arrayOf(RequestMethod.GET))
    fun login(req: HttpServletRequest): String {
        val redirectUri = req.scheme + "://" + req.serverName + ":" + req.serverPort + "/callback"
        val authorizeUrl = authenticationController.buildAuthorizeUrl(req, redirectUri)
        return "redirect:" + authorizeUrl.build()
    }

}