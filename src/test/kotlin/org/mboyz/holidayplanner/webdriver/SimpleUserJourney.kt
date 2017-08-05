package org.mboyz.holidayplanner.webdriver

import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
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
    }

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        user    .visits(HOME)
                .isLoggedOut()
        screen  .showsHome()

        user    .opensHolidayOverview()
        screen  .showsErrorPage()
        user    .visits(HOME)
                .clicksLogin()
                .isLoggedIn()

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

//        user    .createsHoliday(HOLIDAY_WITH_INVALID_DATE)
//        screen  .doesNotShowHoliday(HOLIDAY_WITH_INVALID_DATE)

        user    .clicksLogout()
                .isLoggedOut()
        screen  .showsHome()
    }
}
