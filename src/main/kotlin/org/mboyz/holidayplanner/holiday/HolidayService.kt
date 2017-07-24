package org.mboyz.holidayplanner.holiday

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class HolidayService(@Autowired val holidayRepository: HolidayRepository) {
    fun findAll(): Iterable<Holiday> {
        return holidayRepository.findAll()
    }

    fun findOne(id: Long): Holiday? {
        return holidayRepository.findOne(id)
    }

    fun save(holiday: Holiday): Holiday? {
        return holidayRepository.save(holiday)
    }

}