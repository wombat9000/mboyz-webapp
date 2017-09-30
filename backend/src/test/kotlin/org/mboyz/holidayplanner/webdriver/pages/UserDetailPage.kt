package org.mboyz.holidayplanner.webdriver.pages

import org.openqa.selenium.By
import org.openqa.selenium.WebDriver

class UserDetailPage(private val webDriver: WebDriver) {
    fun getParticipations(): List<String> {
        val links = webDriver.findElement(By.id("holidays")).findElements(By.tagName("a"))
        return links.map { it.text }
    }
}