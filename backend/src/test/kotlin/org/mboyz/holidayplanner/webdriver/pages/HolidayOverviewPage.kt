package org.mboyz.holidayplanner.webdriver.pages

import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class HolidayOverviewPage(private val webDriver: WebDriver) {
    fun opensDetailPageOf(holiday: HolidayEntity): HolidayDetailPage {
        val cards = webDriver.findElements(By.cssSelector("div.card-block"))
        val holidayCard = cards.first { it.findElement(By.tagName("h4")).text == holiday.name }
        holidayCard.findElement(By.tagName("a")).click()
        return HolidayDetailPage(webDriver)
    }
}