package org.mboyz.holidayplanner.webdriver

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.openqa.selenium.By

class SimpleUserJourney : AbstractWebdriverTest(){

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        // visits home
        webDriver.navigate().to(HOME)

        // not logged in user
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-right li a")).getAttribute("text")
        assertThat(loginButtonText, `is`("Login"))

        // tries to visit urlaub
        webDriver.findElement(By.cssSelector("ul.nav.navbar-nav li a[href='/holidays']")).click()

        // gets redirected to failed auth page
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h2")).text
        assertThat(unauthenticatedInfo, `is`("Du musst einloggen, um diese Seite zu sehen."))

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