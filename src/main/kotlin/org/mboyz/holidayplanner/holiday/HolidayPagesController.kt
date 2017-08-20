package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.User
import org.mboyz.holidayplanner.user.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView
import java.security.Principal

@Controller
@RequestMapping("/holiday")
class HolidayPagesController
@Autowired
constructor(val holidayService: HolidayService, val userService: UserService) {

    @ModelAttribute(name = "allHolidays")
    fun allHolidays(): List<Holiday> {
        return holidayService.findAll()
                .filter { it.notDeleted() }
                .toList()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun index(): String {
        return "holiday/index"
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun detail(@PathVariable id: Long, principal: Principal): ModelAndView {
        val holiday = holidayService.findOne(id)

        val modelAndView = ModelAndView("holiday/detail", "holiday", holiday)
        modelAndView.addObject("isParticipating", isParticipating(principal, id))

        return modelAndView
    }

    fun isParticipating(principal: Principal, holidayId: Long): Boolean {
        val user: User = userService.findByFbId(principal.name) ?: return false
        return user.participations.any { x -> x.holiday?.id == holidayId }
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.GET))
    fun create(model: Model): String {
        model.addAttribute(Holiday())
        return "holiday/form"
    }

    @ResponseStatus(HttpStatus.FOUND)
    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    fun save(holiday: Holiday): String {
        val savedHoliday = holidayService.save(holiday)
        return "redirect:/holiday/${savedHoliday!!.id}"
    }
}