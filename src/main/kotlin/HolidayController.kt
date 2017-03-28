package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class HolidayController {

    @RequestMapping("/")
    @ResponseBody
    fun test(): String {
        println("test")
        return "Hello"
    }
}