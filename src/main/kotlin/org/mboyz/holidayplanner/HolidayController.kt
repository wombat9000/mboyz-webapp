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
               @RequestParam(required = false) endDate: String?,
               response: HttpServletResponse): Holiday? {

        val startDate: LocalDate? = if(startDate.isNullOrEmpty()) null else LocalDate.parse(startDate)
        val endDate: LocalDate? = if(endDate.isNullOrEmpty()) null else LocalDate.parse(endDate)

        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            response.status = SC_BAD_REQUEST
            return null
        }

        response.status = SC_CREATED
        return holidayRepository.save(Holiday(
                name = name,
                location = location ?: "",
                startDate = startDate,
                endDate = endDate))
    }
}