package org.mboyz.holidayplanner

import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.ResponseBody
import java.time.LocalDate

@Controller
@RequestMapping("/holiday")
class HolidayController(@Autowired val holidayRepository: HolidayRepository) {

    @RequestMapping(value = "/index", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun index(): List<Holiday> {
        val findAll: List<Holiday> = holidayRepository.findAll().toList()
        return findAll
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun create(@RequestParam name: String,
               @RequestParam(required = false) location: String?,
               @RequestParam(required = false) startDate: String?,
               @RequestParam(required = false) endDate: String?): Holiday {

        val startDate: LocalDate? = if(startDate.isNullOrEmpty()) null else LocalDate.parse(startDate)
        val endDate: LocalDate? = if(endDate.isNullOrEmpty()) null else LocalDate.parse(endDate)

        return holidayRepository.save(Holiday(
                name = name,
                location = location ?: "",
                startDate = startDate,
                endDate = endDate))
    }
}