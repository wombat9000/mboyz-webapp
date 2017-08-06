package org.mboyz.holidayplanner.webdriver.api

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.mboyz.holidayplanner.holiday.Holiday
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ScreenApi(val webDriver: WebDriver) {
    fun showsErrorPage(): ScreenApi {
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h1")).text
        assertThat(unauthenticatedInfo, `is`("Es ist ein Fehler aufgetreten."))
        return this
    }

    fun showsHolidayOverview():ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Alle Urlaube"))
        return this
    }

    fun showsNoHolidays(): ScreenApi {
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        assertTrue(rows.isEmpty())
        return this
    }

    fun showsHolidays(vararg holidays: Holiday): ScreenApi {
        val rows: MutableList<WebElement> = webDriver.findElements(By.cssSelector("table tbody tr"))

        holidays.forEach { it -> it assertIsIncludedIn rows }
        return this
    }

    fun showsHome(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Home"))
        return this
    }

    fun showsPageForHoliday(holiday: Holiday): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        val location = webDriver.findElements(By.cssSelector("div.holiday_details ul li"))[0].text

        assertThat("holiday name is the page heading", pageHeading, containsString(holiday.name))
        assertThat("holiday location is shown", location, containsString(holiday.location))
        return this
    }
}

private infix fun Holiday.assertIsIncludedIn(rows: MutableList<WebElement>): Unit {
    val holidayIsInRows = rows
            .map { it.findElements(By.tagName("td")) }
            .any { tdList -> this.matches(tdList) }

    assertThat("rows contain $this", holidayIsInRows, `is`(true))
}

private fun Holiday.matches(tdList: List<WebElement>): Boolean {
    assertThat(tdList.size, `is`(5))

    if(tdList[0].text != this.name) return false
    if(tdList[1].text != this.location) return false
    if(tdList[2].text != this.startDate.toString()) return false
    if(tdList[3].text != this.endDate.toString()) return false
    if(tdList[4].text != this.participations.size.toString()) return false

    return true
}

