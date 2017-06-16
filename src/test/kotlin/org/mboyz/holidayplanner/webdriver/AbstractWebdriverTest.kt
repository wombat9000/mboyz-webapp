package org.mboyz.holidayplanner.webdriver

import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.openqa.selenium.Dimension
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.boot.context.embedded.LocalServerPort
import java.net.InetAddress

abstract class AbstractWebdriverTest : AbstractSpringTest() {
    companion object {
        lateinit var webDriver: WebDriver

        @BeforeClass @JvmStatic fun setUp() {
//            System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")
            val caps = DesiredCapabilities()
            caps.isJavascriptEnabled = true
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "node_modules/phantomjs-prebuilt/bin/phantomjs")

            webDriver = PhantomJSDriver(caps)
            webDriver.manage().window().size = Dimension(1920, 1080)
        }

        @After fun after() {
            webDriver.close()
        }

        @AfterClass @JvmStatic fun cleanUp() {
            webDriver.quit()
        }
    }

    @LocalServerPort var port: Int? = null
    var contextPath: String? = InetAddress.getLocalHost().hostAddress
}