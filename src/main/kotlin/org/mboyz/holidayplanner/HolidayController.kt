package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/holiday")
class HolidayController() {

    @RequestMapping("/")
    fun hello(model: Model, @RequestParam(value = "name", required = false, defaultValue = "World") name: String): String {
        model.addAttribute("name", "bastian")
        return "hello"
    }


    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun create(): String {
        println("got hit!")
        return "created"
    }
}