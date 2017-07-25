package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class HolidayService(@Autowired val holidayRepository: HolidayRepository) {
    fun findAll(): Iterable<Holiday> {
        return holidayRepository.findAll()
    }

    fun findOne(id: Long): Holiday? {
        return holidayRepository.findOne(id)
    }

    fun save(holiday: Holiday): Holiday? {
        val parsedStartDate: LocalDate? = holiday.startDate
        val parsedEndDate: LocalDate? = holiday.endDate

        if (invalidTimeFrame(parsedEndDate, parsedStartDate)) {
            throw InvalidDateRangeException()
        }

        return holidayRepository.save(holiday)
    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}