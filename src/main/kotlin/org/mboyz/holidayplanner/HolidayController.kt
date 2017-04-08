package org.mboyz.holidayplanner

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody


@Controller
@RequestMapping("/")
class HolidayController() {

    @RequestMapping("/")
    fun hello(model: Model, @RequestParam(value = "name", required = false, defaultValue = "World") name: String): String {
        model.addAttribute("name", "bastian")
        return "hello"
    }

    @RequestMapping("/.well-known/acme-challenge/OOoxUBhW6UNzrsy4nS3blt-6y39ixWSUJxLHKxj_hYg")
    @ResponseBody
    fun acmeChallenge(): String {
        return "OOoxUBhW6UNzrsy4nS3blt-6y39ixWSUJxLHKxj_hYg.-_7eA0VFURUq6JOdLnLrAilww8Z4w7K-ekp7wX8e7vw"
    }
}