package org.mboyz.holidayplanner.webdriver.api

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Assert.assertTrue
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement

class ScreenApi(val webDriver: WebDriver) {
    fun showsUnauthNotice(): ScreenApi {
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h1")).text
        assertThat(unauthenticatedInfo, `is`("Du kannst diese Seite nicht sehen, da du nicht eingeloggt bist."))
        return this
    }

    fun showsHolidayOverview(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Alle Urlaube"))
        return this
    }

    fun showsNoHolidays(): ScreenApi {
        val cards = webDriver.findElements(By.cssSelector("div.card"))
        assertTrue(cards.isEmpty())
        return this
    }

    fun showsHolidays(vararg holidays: HolidayEntity): ScreenApi {
        val cards: MutableList<WebElement> = webDriver.findElements(By.cssSelector("div.card"))

        holidays.forEach { it -> it assertIsIncludedIn cards }
        return this
    }

    fun showsHome(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Home"))
        return this
    }

    fun showsPageForHoliday(holiday: HolidayEntity): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        val location = webDriver.findElements(By.cssSelector("div.holiday_details ul li"))[0].text

        assertThat("holiday name is the page heading", pageHeading, containsString(holiday.name))
        assertThat("holiday location is shown", location, containsString(holiday.location))
        return this
    }

    fun showsUsers(vararg users: UserEntity): ScreenApi {
        val userRows: MutableList<WebElement> = webDriver.findElements(By.cssSelector("tbody tr"))

        val namesDisplayed: List<String> = userRows.map { it.findElement(By.tagName("a")).text }

        val familyNames = users.map { "${it.givenName} ${it.familyName}" }

        assertTrue(namesDisplayed.containsAll(familyNames))
        return this
    }

    fun doesNotShowHoliday(holiday: HolidayEntity): ScreenApi {
        val cards: MutableList<WebElement> = webDriver.findElements(By.cssSelector("div.card"))

        holiday assertIsNotIncludedIn cards
        return this
    }

    fun showsParticipation(user: UserEntity): ScreenApi {
        webDriver.findElement(By.linkText(user.givenName))
        return this
    }
}

private infix fun HolidayEntity.assertIsIncludedIn(cards: MutableList<WebElement>) {
    val cardHeadings = cards
            .map { it.findElement(By.tagName("h4")).text }
            .any { it == this.name }

    assertThat("rows contain $this", cardHeadings, `is`(true))
}

private infix fun HolidayEntity.assertIsNotIncludedIn(cards: MutableList<WebElement>) {
    val cardHeadings = cards
            .map { it.findElement(By.tagName("h4")).text }
            .none { it == this.name }

    assertThat("rows do not contain $this", cardHeadings, `is`(true))
}
