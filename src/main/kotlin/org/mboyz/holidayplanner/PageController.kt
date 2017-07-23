package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class PageController {

    @RequestMapping("/*")
    fun homeReact(): String {
        return "homeReact"
    }

    @RequestMapping("/start")
    fun home(): String {
        return "home"
    }

    @RequestMapping("/holidays/*")
    fun holidays(): String {
        return "homeReact"
    }
}