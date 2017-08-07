package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal

@Controller
@RequestMapping("/holiday/{id}/")
class HolidayActionsController(@Autowired val holidayService: HolidayService) {

    @RequestMapping(value = "/participate", method = arrayOf(RequestMethod.GET))
    fun participate(@PathVariable id: Long,
               principal: Principal): String {
        holidayService.registerParticipation(id, principal.name)
        return "redirect:/holiday/$id"
    }

    @RequestMapping(value = "/unparticipate", method = arrayOf(RequestMethod.GET))
    fun unparticipate(@PathVariable id: Long,
               principal: Principal): String {
        holidayService.removeParticipation(id, principal.name)
        return "redirect:/holiday/$id"
    }
}