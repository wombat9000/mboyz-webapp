package org.mboyz.holidayplanner

import org.mboyz.holidayplanner.user.UserMapper
import org.mboyz.holidayplanner.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam



@Controller
@RequestMapping("/holiday")
class HolidayController(@Autowired val userMapper: UserMapper) {


    @RequestMapping(method = arrayOf(RequestMethod.GET), value = "/")
    fun test(): String {
        println("test")
        return "index"
    }

    @RequestMapping("/hello")
    fun hello(model: Model, @RequestParam(value = "name", required = false, defaultValue = "World") name: String): String {
        println(user)
        model.addAttribute("name", "" + user.id)
        return "hello"
    }
}