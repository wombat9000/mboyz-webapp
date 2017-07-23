package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.ModelAttribute
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@Controller
@RequestMapping("/holiday")
class HolidayPagesController(@Autowired val holidayRepository: HolidayRepository) {

        @ModelAttribute(name = "allHolidays")
        fun allHolidays(): List<Holiday> {
            return holidayRepository.findAll().toList()
        }

        @RequestMapping(value = "/", method = arrayOf(RequestMethod.GET))
        fun index(): String {
            return "holidayOverview"
        }
}