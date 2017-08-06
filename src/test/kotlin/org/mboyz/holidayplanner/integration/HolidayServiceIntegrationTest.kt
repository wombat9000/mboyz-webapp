package org.mboyz.holidayplanner.integration

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.contains
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayService
import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.User
import org.springframework.beans.factory.annotation.Autowired


class HolidayServiceIntegrationTest : AbstractSpringTest() {

    @Autowired
    lateinit var testee: HolidayService

    @Test
    fun shouldAddParticipationAndUpdateRelatedEntities() {
        val originalHoliday = testee.save(Holiday())!!
        val fbId = "someFbId"
        val originalUser = userService.save(User(fbId = fbId))

        val participation: Participation = testee.registerParticipation(originalHoliday.id, fbId).participations.first()

        val participatingUser = userService.findOne(originalUser.id)!!
        val participatedHoliday = testee.findOne(originalHoliday.id)

        assertThat(participatingUser.participations, contains(participation))
        assertThat(participatedHoliday.participations, contains(participation))
    }
}