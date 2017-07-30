package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/holiday")
class HolidayPagesController(@Autowired val holidayService: HolidayService) {

    @ModelAttribute(name = "allHolidays")
    fun allHolidays(): List<Holiday> {
        return holidayService.findAll().toList()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun index(): String {
        return "holiday/index"
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun detail(@PathVariable id: Long): ModelAndView {
        val holiday = holidayService.findOne(id)
        return ModelAndView("holiday/detail", "holiday", holiday)
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