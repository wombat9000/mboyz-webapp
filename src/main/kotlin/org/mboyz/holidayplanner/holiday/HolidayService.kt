package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Component
@Transactional
open class HolidayService
@Autowired
constructor(val holidayRepository: HolidayRepository, val userRepository: UserRepository) {

    fun findOne(id: Long): Holiday {
        return holidayRepository.findOne(id) ?: throw HolidayNotFoundException()
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

    fun registerParticipation(holidayId: Long, fbId: String): Holiday {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        val participation = Participation(holiday = holiday, user = user)
        holiday.addParticipation(participation)
        user.addParticipation(participation)
        return holiday
    }

    fun removeParticipation(holidayId: Long, fbId: String) {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        holiday.removeParticipation(user)
        user.removeParticipation(holiday)
    }

    fun softDelete(holidayId: Long, fbId: String) {
        val holidayToDelete = findOne(holidayId)
        val deletingUser = userRepository.findByFbId(fbId)!!

        holidayToDelete.deletedDate = LocalDateTime.now()
        holidayToDelete.deletingUser = deletingUser
    }

    fun deleteAll() {
        holidayRepository.findAll().forEach(Holiday::clearParticipations)
        holidayRepository.findAll().forEach(Holiday::clearComments)
        holidayRepository.deleteAll()
    }

    fun addComment(holidayId: Long, fbId: String, commentBody: String): Holiday {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        val comment = Comment(holiday = holiday, user = user, text = commentBody)
        holiday.addComment(comment)
        user.addComment(comment)
        return holiday
    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}

private fun Holiday.clearComments() {
    this.comments.forEach(Comment::removeReferenceToHoliday)
    this.comments.clear() //To change body of created functions use File | Settings | File Templates.
}

private fun Holiday.clearParticipations() {
    this.participations.forEach(Participation::removeReferenceToHoliday)
    this.participations.clear()
}

private fun Participation.removeReferenceToHoliday() {
    this.holiday = null
}

private fun Comment.removeReferenceToHoliday() {
    this.holiday = null
}
