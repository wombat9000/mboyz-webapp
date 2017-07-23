package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.servlet.ModelAndView

@Controller
@RequestMapping("/holiday")
class HolidayPagesController(@Autowired val holidayRepository: HolidayRepository) {

    @ModelAttribute(name = "allHolidays")
    fun allHolidays(): List<Holiday> {
        return holidayRepository.findAll().toList()
    }

    @RequestMapping(method = arrayOf(RequestMethod.GET))
    fun index(): String {
        return "holidayOverview"
    }

    @RequestMapping(value = "/{id}", method = arrayOf(RequestMethod.GET))
    fun detail(@PathVariable id: Long): ModelAndView {
        val holiday = holidayRepository.findOne(id)
        return ModelAndView("holidayDetailView", "holiday", holiday)
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.GET))
    fun create(model: Model): String {
        model.addAttribute(Holiday())
        return "holidayCreate"
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    fun save(holiday: Holiday): String {
        holidayRepository.save(holiday)
        return "redirect:/holiday"
    }
}