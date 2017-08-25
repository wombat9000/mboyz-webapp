package org.mboyz.holidayplanner

import org.springframework.security.access.annotation.Secured
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping


@Controller
class Auth0StubController {

    @RequestMapping(value = "/auth0Test")
    @Secured("ROLE_ANONYMOUS")
    fun someTestRoute(): String {
        return "redirect:/callback"
    }
}