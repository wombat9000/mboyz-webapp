package org.mboyz.holidayplanner

import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody

@Controller
@RequestMapping("/holiday")
class HolidayController(@Autowired val holidayRepository: HolidayRepository) {

    @RequestMapping(value = "/index", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun index(): List<Holiday> {
        val findAll: List<Holiday> = holidayRepository.findAll().toList()
        return findAll
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST))
    @ResponseBody
    fun create(@RequestParam name: String): String {

        val holiday: Holiday = Holiday(name = name)
        holidayRepository.save(holiday)

        return "created"
    }
}