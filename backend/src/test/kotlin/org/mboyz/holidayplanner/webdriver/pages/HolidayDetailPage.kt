package org.mboyz.holidayplanner.webdriver.pages

import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class HolidayDetailPage(private val webDriver: WebDriver) {
    private val participationLink = webDriver.findElement(By.linkText("Ich nehme teil."))

    fun clickParticipate(): HolidayDetailPage {
        participationLink.click()
        return this
    }

    fun visitParticipant(user: UserEntity): UserDetailPage {
        webDriver.findElement(By.linkText(user.givenName)).click()
        return UserDetailPage(webDriver)
    }

    fun getParticipants(): List<String> {
        val participants = webDriver.findElement(By.id("participants"))
        return participants.findElements(By.tagName("a")).map { it.text }
    }
}