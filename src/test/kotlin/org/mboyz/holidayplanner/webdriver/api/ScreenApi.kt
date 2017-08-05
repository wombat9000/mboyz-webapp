package org.mboyz.holidayplanner.webdriver.api

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Assert
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.isIncludedIn
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class ScreenApi(val webDriver: WebDriver) {
    fun showsErrorPage(): ScreenApi {
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h1")).text
        MatcherAssert.assertThat(unauthenticatedInfo, CoreMatchers.`is`("Es ist ein Fehler aufgetreten."))
        return this
    }

    fun showsHolidayOverview():ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        MatcherAssert.assertThat(pageHeading, CoreMatchers.`is`("Alle Urlaube"))
        return this
    }

    fun showsNoHolidays(): ScreenApi {
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        Assert.assertTrue(rows.isEmpty())
        return this
    }

    fun showsHolidays(vararg holidays: Holiday): ScreenApi {
        val rows = webDriver.findElements(By.cssSelector("table tbody tr")).map { it -> it.text }
        holidays.forEach { it -> it isIncludedIn rows }
        return this
    }

    fun showsHome(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        MatcherAssert.assertThat(pageHeading, CoreMatchers.`is`("Home"))
        return this
    }

    fun showsPageForHoliday(holiday: Holiday): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        val location = webDriver.findElements(By.cssSelector("div.holiday_details ul li"))[0].text

        MatcherAssert.assertThat("holiday name is the page heading", pageHeading, CoreMatchers.containsString(holiday.name))
        MatcherAssert.assertThat("holiday location is shown", location, CoreMatchers.containsString(holiday.location))
        return this
    }
}