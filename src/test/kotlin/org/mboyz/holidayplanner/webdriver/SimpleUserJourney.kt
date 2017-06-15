package org.mboyz.holidayplanner.webdriver

import com.sun.tools.hat.internal.model.JavaInt
import org.junit.AfterClass
import org.junit.BeforeClass
import org.junit.Test
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import org.openqa.selenium.WebElement
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.remote.DesiredCapabilities
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.springframework.boot.context.embedded.LocalServerPort



class Selenium : AbstractSpringTest(){
    companion object {
        lateinit var webDriver: WebDriver

//        @LocalServerPort
//        var randomServerPort: Int?

        @BeforeClass @JvmStatic fun setUp() {
            System.setProperty("phantomjs.binary.path", "/usr/local/bin/phantomjs")

            val caps = DesiredCapabilities()
            caps.isJavascriptEnabled = true
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "/usr/local/bin/phantomjs")

            webDriver = PhantomJSDriver(caps)
//            webDriver = FirefoxDriver(caps)
        }

        @AfterClass @JvmStatic fun cleanUp() {
            webDriver.close()
        }
    }

    @Test
    fun test() {
//        webDriver.navigate().to("http://localhost:8080")
//        println(randomServerPort)
        webDriver.get("http://localhost:8080")

        // not logged in user
//        val test: WebElement = webDriver.findElement(By.cssSelector("div.navbar-collapse a[role='button']"))
        val test: WebElement = webDriver.findElement(By.id("root"))

        // is on "Home"

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