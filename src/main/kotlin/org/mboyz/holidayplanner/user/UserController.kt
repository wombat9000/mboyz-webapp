package org.mboyz.holidayplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/user")
class UserController(@Autowired val userService: UserService) {

    @RequestMapping(method = arrayOf(RequestMethod.POST), value = "/create")
    fun create(firstName: String,
               lastName: String,
               email: String,
               password: String) {
        userService.create(firstName, lastName, email, password)
    }
}