package org.mboyz.holidayplanner.webdriver

import org.junit.Test
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.user.persistence.UserEntity
import java.time.LocalDate

class UserJourney : AbstractWebdriverTest(){

    companion object {
        val SURF_HOLIDAY = HolidayEntity(
                name = "Surfurlaub",
                location = "Frankreich",
                startDate = LocalDate.parse("2017-05-28"),
                endDate = LocalDate.parse("2017-05-30"))

        val SKI_HOLIDAY = HolidayEntity(
                name = "Skiurlaub",
                location = "Portes du Soleil",
                startDate = LocalDate.parse("2017-03-28"),
                endDate = LocalDate.parse("2017-04-05"))

        val HOLIDAY_WITH_INVALID_DATE = HolidayEntity(
                name = "Some Name",
                location = "Some Location",
                startDate = LocalDate.parse("2017-04-05"),
                endDate = LocalDate.parse("2017-03-28"))

        val BASTIAN = UserEntity(
                givenName = "Bastian",
                familyName = "Stein",
                fbId = "facebook|basti",
                imageUrl = "someImage.jpg"
        )
    }

    @Test
    fun userJourney() {
        init()

        preLogin()

        login()

        singleUserOverview()

        emptyHolidayOverview()

        singleHolidayOverview()

        createNewHoliday()

        createInvalidHoliday()

        participation()

        logout()
    }

    private fun participation() {
        user    .opensHolidayOverview()
                .visitsHoliday(SURF_HOLIDAY)
    }

    private fun init() {
        app     .deleteAllHolidays()
                .deleteAllUsers()
    }

    private fun preLogin() {
        user    .visits(HOME)
                .isLoggedOut()
        screen  .showsHome()

        user    .opensHolidayOverview()
        screen  .showsUnauthNotice()
    }

    private fun createNewHoliday() {
        user    .navigatesToHolidaysCreation()
                .createsHoliday(SURF_HOLIDAY)
        screen  .showsPageForHoliday(SURF_HOLIDAY)

        user    .opensHolidayOverview()
        screen  .showsHolidays(SKI_HOLIDAY, SURF_HOLIDAY)
    }

    private fun createInvalidHoliday() {
        user    .navigatesToHolidaysCreation()
                .createsHoliday(HOLIDAY_WITH_INVALID_DATE)
                .opensHolidayOverview()
        screen  .doesNotShowHoliday(HOLIDAY_WITH_INVALID_DATE)
    }

    private fun singleUserOverview() {
        user    .opensUserOverview()
        screen  .showsUsers(BASTIAN)
    }

    private fun singleHolidayOverview() {
        app     .createHoliday(SKI_HOLIDAY)

        user    .visits(HOME)
                .opensHolidayOverview()
        screen  .showsHolidays(SKI_HOLIDAY)
    }

    private fun emptyHolidayOverview() {
        user    .opensHolidayOverview()
        screen  .showsHolidayOverview()
                .showsNoHolidays()
    }

    private fun login() {
        user    .visits(HOME)
                .loginAs(BASTIAN)
                .isLoggedIn()
    }

    private fun logout() {
        user    .clicksLogout()
                .isLoggedOut()
        screen  .showsHome()
    }
}
