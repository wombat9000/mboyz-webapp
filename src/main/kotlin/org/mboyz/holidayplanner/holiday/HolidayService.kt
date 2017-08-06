package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate

@Component
class HolidayService(
        @Autowired val holidayRepository: HolidayRepository,
        @Autowired val userRepository: UserRepository) {

    fun findOne(id: Long): Holiday {
        return holidayRepository.findOne(id)?: throw HolidayNotFoundException()
    }

    fun findAll(): Iterable<Holiday> {
        return holidayRepository.findAll()
    }

    fun save(holiday: Holiday): Holiday? {
        val parsedStartDate: LocalDate? = holiday.startDate
        val parsedEndDate: LocalDate? = holiday.endDate

        if (invalidTimeFrame(parsedEndDate, parsedStartDate)) {
            throw InvalidDateRangeException()
        }

        return holidayRepository.save(holiday)
    }

    @Transactional
    fun registerParticipation(holidayId: Long, fbId: String): Holiday {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        if (holiday.participations.any { it.user == user }) return holiday

        val participation = Participation(holiday = holiday, user = user)
        user.participations.add(participation)
        holiday.participations.add(participation)

        return holiday
    }

    @Transactional
    fun deleteAll() {
        holidayRepository.findAll().forEach({ it ->
            run {
                it.participations.forEach { participation -> participation.holiday = null }
                it.participations.clear()
            }
        })
        holidayRepository.deleteAll()
    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}