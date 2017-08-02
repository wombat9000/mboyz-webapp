package org.mboyz.holidayplanner.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class PagesController {


    @RequestMapping("/")
    fun home(request: HttpServletRequest, response: HttpServletResponse): String {
        return "home"
    }


}