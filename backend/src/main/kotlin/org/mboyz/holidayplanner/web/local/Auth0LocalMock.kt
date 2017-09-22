package org.mboyz.holidayplanner.web.local

import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Controller
@Profile("local")
@RequestMapping("/auth0")
class Auth0LocalMock {

    @RequestMapping("/authorize")
    fun authorize(request: HttpServletRequest, response: HttpServletResponse): String {
        return "redirect:/callback"
    }
}