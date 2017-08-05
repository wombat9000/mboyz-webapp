package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class HolidayService(@Autowired val holidayRepository: HolidayRepository) {
    fun findAll(): Iterable<Holiday> {
        return holidayRepository.findAll()
    }

    fun findOne(id: Long): Holiday {
        return holidayRepository.findOne(id)?: throw HolidayNotFoundException()
    }

    fun save(holiday: Holiday): Holiday? {
        val parsedStartDate: LocalDate? = holiday.startDate
        val parsedEndDate: LocalDate? = holiday.endDate

        if (invalidTimeFrame(parsedEndDate, parsedStartDate)) {
            throw InvalidDateRangeException()
        }

        return holidayRepository.save(holiday)
    }
//
//    fun signUserUpForHoliday(holidayId: Long, user: User) {
//        val holiday = findOne(holidayId)
//
//        if (holiday.users.contains(user.id)) return
//
//        val combinedParticipants: MutableList<Long> = mutableListOf()
//        combinedParticipants.addAll(holiday.users)
//        combinedParticipants.add(user.id)
//
//        val holidayWithAddedParticipant = holiday.copy(users = combinedParticipants)
//        save(holidayWithAddedParticipant)
//    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}