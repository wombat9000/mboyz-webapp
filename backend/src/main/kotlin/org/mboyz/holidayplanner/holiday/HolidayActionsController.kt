package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.HolidayService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import java.security.Principal

@Controller
@RequestMapping("/holiday/{id}/")
class HolidayActionsController
@Autowired
constructor(val holidayService: HolidayService) {

    @RequestMapping(value = "/add-comment", method = arrayOf(RequestMethod.POST))
    fun addComment(@PathVariable id: Long,
                   comment: String,
                   principal: Principal): String {
        holidayService.addComment(id, principal.name, comment)
        return "redirect:/holiday/$id"
    }

    @RequestMapping(value = "/participate", method = arrayOf(RequestMethod.GET))
    fun participate(@PathVariable id: Long, principal: Principal): String {
        holidayService.registerParticipation(id, principal.name)
        return "redirect:/holiday/$id"
    }

    @RequestMapping(value = "/unparticipate", method = arrayOf(RequestMethod.GET))
    fun unparticipate(@PathVariable id: Long, principal: Principal): String {
        holidayService.removeParticipation(id, principal.name)
        return "redirect:/holiday/$id"
    }

    @RequestMapping(value = "/delete", method = arrayOf(RequestMethod.GET))
    fun delete(@PathVariable id: Long, principal: Principal): String {
        holidayService.softDelete(id, principal.name)
        return "redirect:/holiday"
    }
}