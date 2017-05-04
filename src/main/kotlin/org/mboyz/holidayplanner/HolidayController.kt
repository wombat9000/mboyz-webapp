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
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletResponse.SC_BAD_REQUEST
import javax.servlet.http.HttpServletResponse.SC_CREATED

@Controller
@RequestMapping("/api/holiday")
class HolidayController(@Autowired val holidayRepository: HolidayRepository) {

    @RequestMapping(value = "/index", method = arrayOf(RequestMethod.GET), produces = arrayOf("application/json; charset=UTF-8"))
    @ResponseBody
    fun index(): List<Holiday> {
        return holidayRepository.findAll().toList()
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
