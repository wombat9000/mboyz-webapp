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
        assertThat(unauthenticatedInfo, `is`("Du kannst diese Seite nicht sehen, da du nicht eingeloggt bist."))
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
        val cards: MutableList<WebElement> = webDriver.findElements(By.cssSelector("div.card"))

        holidays.forEach { it -> it assertIsIncludedIn cards }
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

private infix fun Holiday.assertIsIncludedIn(cards: MutableList<WebElement>): Unit {
    val cardHeadings = cards
            .map { it.findElement(By.tagName("h4")).text }
            .any { it == this.name}

    assertThat("rows contain $this", cardHeadings, `is`(true))
}
