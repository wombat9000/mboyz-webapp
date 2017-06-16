package org.mboyz.holidayplanner.webdriver

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.openqa.selenium.By
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
        lateinit var screen: ScreenApi
        lateinit var user: UserApi

        @BeforeClass @JvmStatic fun setUp() {
//            System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")
            val caps = DesiredCapabilities()
            caps.isJavascriptEnabled = true
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "node_modules/phantomjs-prebuilt/bin/phantomjs")

            webDriver = PhantomJSDriver(caps)
            webDriver.manage().window().size = Dimension(1920, 1080)

            screen = ScreenApi(webDriver)
            user = UserApi(webDriver)
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

class UserApi(val webDriver: WebDriver) {

    fun visit(url: String): UserApi {
        webDriver.navigate().to(url)
        return this
    }

    fun navigateToHolidaysPage(): UserApi {
        webDriver.findElement(By.cssSelector("ul.nav.navbar-nav li a[href='/holidays']")).click()
        return this
    }
}

class ScreenApi(val webDriver: WebDriver) {
    fun showsLoginButton(): ScreenApi {
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-right li a")).getAttribute("text")
        MatcherAssert.assertThat(loginButtonText, CoreMatchers.`is`("Login"))
        return this
    }

    fun showsUnauthInfo(): ScreenApi {
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h2")).text
        MatcherAssert.assertThat(unauthenticatedInfo, CoreMatchers.`is`("Du musst einloggen, um diese Seite zu sehen."))
        return this
    }
}
