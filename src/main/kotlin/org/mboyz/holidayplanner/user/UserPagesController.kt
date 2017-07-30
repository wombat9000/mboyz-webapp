package org.mboyz.holidayplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/user")
class UserPagesController(@Autowired val userService: UserService) {

    @ModelAttribute(name = "allUsers")
    fun allUsers(): List<User> {
        return userService.findAll().toList()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun index(): String {
        return "user/index"
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun detail(@PathVariable id: Long): ModelAndView {
        val user = userService.findOne(id)
        return ModelAndView("user/detail", "user", user)
    }
}