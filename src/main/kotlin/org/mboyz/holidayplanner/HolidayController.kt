package org.mboyz.holidayplanner

import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.*
import java.time.LocalDate
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.*

@Controller
@RequestMapping("/api/holiday")
class HolidayController(@Autowired val holidayRepository: HolidayRepository) {

    @RequestMapping(value = "/index", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun index(): List<Holiday> {
        return holidayRepository.findAll().toList()
    }

    @RequestMapping(value = "/{holidayId}", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun get(@PathVariable holidayId: Long,
            response: HttpServletResponse): Holiday? {


        val holiday: Holiday? = holidayRepository.findOne(holidayId)

        if (holiday == null) {
            response.status = SC_NO_CONTENT
        }

        return holiday
    }

    @RequestMapping(value = "/create", method = arrayOf(RequestMethod.POST), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun create(holiday: Holiday,
               response: HttpServletResponse): Holiday? {

        val parsedStartDate: LocalDate? = holiday.startDate
        val parsedEndDate: LocalDate? = holiday.endDate

        if (invalidTimeFrame(parsedEndDate, parsedStartDate)) {
            response.status = SC_BAD_REQUEST
            return null
        }

        response.status = SC_CREATED
        return holidayRepository.save(holiday)
    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}
