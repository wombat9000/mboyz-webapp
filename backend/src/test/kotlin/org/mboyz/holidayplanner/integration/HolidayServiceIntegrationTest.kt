package org.mboyz.holidayplanner.integration

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Assert.assertTrue
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Comment
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.UserEntity
import org.springframework.beans.factory.annotation.Autowired


class HolidayServiceIntegrationTest : AbstractSpringTest() {

    @Autowired
    lateinit var testee: HolidayService

    @Test
    fun shouldAddParticipationAndUpdateRelatedEntities() {
        val originalHoliday = testee.save(Holiday())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))

        val participation: Participation = testee.registerParticipation(originalHoliday.id, fbId).participations.first()

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertThat(participatingUser.participations, contains(participation))
        assertThat(participatedHoliday.participations, contains(participation))
    }

    @Test
    fun shouldNotAddParticipationIfAlreadyExists() {
        val originalHoliday = testee.save(Holiday())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))
        val participation: Participation = testee.registerParticipation(originalHoliday.id, fbId).participations.first()
        testee.registerParticipation(originalHoliday.id, fbId).participations.first()

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertThat(participatingUser.participations, contains(participation))
        assertThat(participatedHoliday.participations, contains(participation))
    }

    @Test
    fun shouldRemoveParticipation() {
        val originalHoliday = testee.save(Holiday())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))
        testee.registerParticipation(originalHoliday.id, fbId).participations.first()

        testee.removeParticipation(originalHoliday.id, fbId)

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertTrue(participatingUser.participations.isEmpty())
        assertTrue(participatedHoliday.participations.isEmpty())
    }

    @Test
    fun shouldAddCommentAndUpdateRelatedEntities() {
        val originalHoliday = testee.save(Holiday())!!
        val fbId = "someFbId"
        val originalUser = userService.save(UserEntity(fbId = fbId))

        val commentToAdd = "someComment"

        val comment: Comment = testee.addComment(originalHoliday.id, fbId, commentToAdd).comments.first()

        val author = userService.findOne(originalUser.id)!!
        val subject = testee.findOne(originalHoliday.id)

        assertThat(author.comments, contains(comment))
        assertThat(subject.comments, contains(comment))
    }

    @Test
    fun shouldSoftDelete() {
        val someHoliday = testee.save(Holiday())!!
        val someUser = userService.save(UserEntity(fbId = "someFbId"))

        testee.softDelete(someHoliday.id, someUser.fbId)

        val result = testee.findOne(someHoliday.id)

        assertThat(result.deletedDate, notNullValue())
        assertThat(result.deletingUser, `is`(someUser))
    }
}