package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam



@Controller
@RequestMapping("/holiday")
class HolidayController {

    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/")
    fun test(): String {
        println("test")
        return "index"
    }

    @RequestMapping("/hello")
    fun hello(model: Model, @RequestParam(value = "name", required = false, defaultValue = "World") name: String): String {
        model.addAttribute("name", name)
        return "hello"
    }
}