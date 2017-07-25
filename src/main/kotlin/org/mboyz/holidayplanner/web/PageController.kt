package org.mboyz.holidayplanner.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal


@Controller
class PageController {

    @RequestMapping("/")
    fun homeReact(): String {
        return "home"
    }

    @RequestMapping(value = "/portal/home", method = arrayOf(RequestMethod.GET))
    protected fun home(model: MutableMap<String, Any>, principal: Principal?): String {
        if (principal == null) {
            return "redirect:/logout"
        }
        model.put("userId", principal)
        return "home"
    }
}