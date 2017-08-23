package org.mboyz.holidayplanner.webdriver

import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.user.User
import java.time.LocalDate

class SimpleUserJourney : AbstractWebdriverTest(){

    companion object {
        val SURF_HOLIDAY = Holiday(
                name = "Surfurlaub",
                location = "Frankreich",
                startDate = LocalDate.parse("2017-05-28"),
                endDate = LocalDate.parse("2017-05-30"))

        val SKI_HOLIDAY = Holiday(
                name = "Skiurlaub",
                location = "Portes du Soleil",
                startDate = LocalDate.parse("2017-03-28"),
                endDate = LocalDate.parse("2017-04-05"))

        val HOLIDAY_WITH_INVALID_DATE = Holiday(
                name = "Some Name",
                location = "Some Location",
                startDate = LocalDate.parse("2017-04-05"),
                endDate = LocalDate.parse("2017-03-28"))

        val BASTIAN = User(
                givenName = "Bastian",
                familyName = "Stein",
                fbId = "facebook|basti",
                imageUrl = "someImage.jpg"
        )
    }

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        app     .deleteAllHolidays()
                .deleteAllUsers()

        user    .visits(HOME)
                .isLoggedOut()
        screen  .showsHome()

        user    .opensHolidayOverview()
        screen  .showsUnauthNotice()
        user    .visits(HOME)
                .loginAs(BASTIAN)
                .isLoggedIn()

        user    .opensUserOverview()
        screen  .showsUsers(BASTIAN)

        user    .opensHolidayOverview()
        screen  .showsHolidayOverview()
                .showsNoHolidays()

        app     .createHoliday(SKI_HOLIDAY)

        user    .visits(HOME)
                .opensHolidayOverview()
        screen  .showsHolidays(SKI_HOLIDAY)

        user    .navigatesToHolidaysCreation()
                .createsHoliday(SURF_HOLIDAY)
        screen  .showsPageForHoliday(SURF_HOLIDAY)

        user    .opensHolidayOverview()
        screen  .showsHolidays(SKI_HOLIDAY, SURF_HOLIDAY)

        user    .opensHolidayOverview()
        screen  .showsHolidays(SURF_HOLIDAY)

        user    .navigatesToHolidaysCreation()
                .createsHoliday(HOLIDAY_WITH_INVALID_DATE)
                .opensHolidayOverview()
        screen  .doesNotShowHoliday(HOLIDAY_WITH_INVALID_DATE)

        user    .clicksLogout()
                .isLoggedOut()
        screen  .showsHome()
    }
}
