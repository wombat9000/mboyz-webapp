package org.mboyz.holidayplanner.webdriver.pages

import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class UserDetailPage(private val webDriver: WebDriver) {

    fun showsParticipation(holiday: HolidayEntity): UserDetailPage {
        webDriver.findElement(By.linkText(holiday.name))
        return this
    }
}