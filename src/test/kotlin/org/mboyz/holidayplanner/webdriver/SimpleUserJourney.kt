package org.mboyz.holidayplanner.webdriver

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.openqa.selenium.By

class SimpleUserJourney : AbstractWebdriverTest(){

    @Test
    fun simpleUserJourney() {
        // visits home
        webDriver.get("http://$contextPath:$port")

        // not logged in user
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-right li a")).getAttribute("text")
        assertThat(loginButtonText, `is`("Login"))

        // tries to visit urlaub

        // gets redirected to failed auth page

        // clicks login

        // sees login prompt

        // fake login with facebook (?)

        // visits urlaub page

        // sees no urlaube

        // creates one urlaub

        // sees one urlaub

        // logs out
    }
}