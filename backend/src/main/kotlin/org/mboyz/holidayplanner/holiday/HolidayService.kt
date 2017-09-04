package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.persistence.CommentEntity
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.holiday.persistence.HolidayRepository
import org.mboyz.holidayplanner.holiday.persistence.ParticipationEntity
import org.mboyz.holidayplanner.user.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime

@Component
@Transactional
class HolidayService
@Autowired
constructor(val holidayRepository: HolidayRepository, val userRepository: UserRepository) {

    fun findOne(id: Long): HolidayEntity {
        return holidayRepository.findOne(id) ?: throw HolidayNotFoundException()
    }

    fun findAll(): Iterable<HolidayEntity> {
        return holidayRepository.findAll()
    }

    fun save(holiday: HolidayEntity): HolidayEntity? {
        val parsedStartDate: LocalDate? = holiday.startDate
        val parsedEndDate: LocalDate? = holiday.endDate

        if (invalidTimeFrame(parsedEndDate, parsedStartDate)) {
            throw InvalidDateRangeException()
        }

        return holidayRepository.save(holiday)
    }

    fun registerParticipation(holidayId: Long, fbId: String) {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        val participation = ParticipationEntity(holiday = holiday, user = user)
        holiday.addParticipation(participation)
        user.addParticipation(participation)
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
        holidayRepository.findAll().forEach(HolidayEntity::clearParticipations)
        holidayRepository.findAll().forEach(HolidayEntity::clearComments)
        holidayRepository.deleteAll()
    }

    fun addComment(holidayId: Long, fbId: String, commentBody: String): HolidayEntity {
        val holiday = this.findOne(holidayId)
        val user = userRepository.findByFbId(fbId)!!

        val comment = CommentEntity(holiday = holiday, user = user, text = commentBody)
        holiday.addComment(comment)
        user.addComment(comment)
        return holiday
    }

    private fun invalidTimeFrame(endDate: LocalDate?, startDate: LocalDate?): Boolean {
        return startDate != null && endDate != null && startDate.isAfter(endDate)
    }
}

private fun HolidayEntity.clearComments() {
    this.comments.forEach(CommentEntity::removeReferenceToHoliday)
    this.comments.clear() //To change body of created functions use File | Settings | File Templates.
}

private fun HolidayEntity.clearParticipations() {
    this.participations.forEach(ParticipationEntity::removeReferenceToHoliday)
    this.participations.clear()
}

private fun ParticipationEntity.removeReferenceToHoliday() {
    this.holiday = null
}

private fun CommentEntity.removeReferenceToHoliday() {
    this.holiday = null
}
