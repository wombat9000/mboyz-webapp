package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PageController {

    @RequestMapping("/*")
    fun home(): String {
        return "home"
    }
}