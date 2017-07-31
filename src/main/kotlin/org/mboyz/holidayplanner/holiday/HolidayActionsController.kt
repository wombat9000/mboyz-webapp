package org.mboyz.holidayplanner.holiday

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal

@Controller
@RequestMapping("/holiday/{id}/")
class HolidayActionsController {

    @RequestMapping(value = "/participate", method = arrayOf(RequestMethod.GET))
    fun participate(@PathVariable id: Long,
               principal: Principal): String {
        return "redirect:/holiday/$id"
    }
}