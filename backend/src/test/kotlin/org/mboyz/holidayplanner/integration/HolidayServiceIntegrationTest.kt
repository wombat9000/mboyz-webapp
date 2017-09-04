package org.mboyz.holidayplanner.integration

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mboyz.holidayplanner.holiday.persistence.CommentEntity
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.persistence.ParticipationEntity
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.springframework.beans.factory.annotation.Autowired

class HolidayServiceIntegrationTest : AbstractSpringTest() {

    @Autowired
    lateinit var testee: HolidayService

    @Test
    fun shouldAddParticipationAndUpdateRelatedEntities() {
        val originalHoliday = testee.save(HolidayEntity())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))

        testee.registerParticipation(originalHoliday.id, fbId)

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertTrue(participatedHoliday.participations.any { it.user!!.id == originalUser.id })
        assertTrue(participatingUser.participations.any { it.holiday!!.id == originalHoliday.id })
    }

    @Test
    fun shouldNotAddParticipationIfAlreadyExists() {
        val originalHoliday = testee.save(HolidayEntity())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))
        testee.registerParticipation(originalHoliday.id, fbId)
        testee.registerParticipation(originalHoliday.id, fbId)

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertThat(participatingUser.participations, hasSize(1))
        assertThat(participatedHoliday.participations, hasSize(1))
    }

    @Test
    fun shouldRemoveParticipation() {
        val originalHoliday = testee.save(HolidayEntity())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))

        testee.registerParticipation(originalHoliday.id, fbId)
        testee.removeParticipation(originalHoliday.id, fbId)

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertTrue(participatingUser.participations.isEmpty())
        assertTrue(participatedHoliday.participations.isEmpty())
    }

    @Test
    fun shouldAddCommentAndUpdateRelatedEntities() {
        val originalHoliday = testee.save(HolidayEntity())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))

        val commentToAdd = "someComment"

        val comment: CommentEntity = testee.addComment(originalHoliday.id, fbId, commentToAdd).comments.first()

        val author = userService.findOne(originalUser.id)!!
        val subject = testee.findOne(originalHoliday.id)

        assertThat(author.comments, contains(comment))
        assertThat(subject.comments, contains(comment))
    }

    @Test
    fun shouldSoftDelete() {
        val someHoliday = testee.save(HolidayEntity())!!
        val someUser = userService.save(UserEntity(fbId = "someFbId"))

        testee.softDelete(someHoliday.id, someUser.fbId)

        val result = testee.findOne(someHoliday.id)

        assertThat(result.deletedDate, notNullValue())
        assertThat(result.deletingUser, `is`(someUser))
    }
}