package org.mboyz.holidayplanner.webdriver

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.boot.context.embedded.LocalServerPort
import java.net.InetAddress
import java.util.concurrent.TimeUnit

abstract class AbstractWebdriverTest : AbstractSpringTest() {
    companion object {
        lateinit var webDriver: WebDriver
        lateinit var screen: ScreenApi
        lateinit var user: UserApi
        lateinit var js: JsApi

        @BeforeClass @JvmStatic fun setUp() {
//            System.setProperty("webdriver.chrome.driver", "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome")
            val caps = DesiredCapabilities()
            caps.isJavascriptEnabled = true
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "node_modules/phantomjs-prebuilt/bin/phantomjs")

            webDriver = PhantomJSDriver(caps)
            webDriver.manage().window().size = Dimension(1920, 1080)
            webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

            screen = ScreenApi(webDriver)
            user = UserApi(webDriver)
            js = JsApi(webDriver as JavascriptExecutor)
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

class JsApi(val js: JavascriptExecutor) {
    fun setLocalStorage(item: String, value: String) {
        js.executeScript(String.format("window.localStorage.setItem('%s','%s');", item, value))
    }

    fun getFromLocalStorage(key: String): String {
        return js.executeScript(String.format("return window.localStorage.getItem('%s');", key)) as String
    }

    fun removeItemFromLocalStorage(item: String) {
        js.executeScript(String.format("window.localStorage.removeItem('%s');", item))
    }

    fun isItemPresentInLocalStorage(item: String): Boolean {
        return js.executeScript(String.format("return window.localStorage.getItem('%s');", item)) != null
    }

    fun clearLocalStorage() {
        js.executeScript(String.format("window.localStorage.clear();"))
    }
}

class UserApi(val webDriver: WebDriver) {

    fun visits(url: String): UserApi {
        webDriver.navigate().to(url)
        return this
    }

    fun navigatesToHolidaysPage(): UserApi {
        webDriver.findElement(By.cssSelector("ul.nav.navbar-nav li a[href='/holidays']")).click()
        return this
    }

    fun clicksLogin(): UserApi {
        webDriver.findElement(By.cssSelector("ul.navbar-right li a")).click()
        return this
    }

    fun createsOneHoliday(): UserApi {
        webDriver.findElement(By.name("name")).sendKeys("someHoliday")
        webDriver.findElement(By.name("location")).sendKeys("someLocation")
        webDriver.findElement(By.cssSelector("form div.form-group button")).click()
        return this
    }

    fun clicksLogout(): UserApi {
        webDriver.findElement(By.cssSelector("ul.navbar-right li a")).click()
        return this
    }
}

class ScreenApi(val webDriver: WebDriver) {
    fun showsLoginButton(): ScreenApi {
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-right li a")).getAttribute("text")
        assertThat(loginButtonText, `is`("Login"))
        return this
    }

    fun showsUnauthInfo(): ScreenApi {
        val unauthenticatedInfo = webDriver.findElement(By.tagName("h2")).text
        assertThat(unauthenticatedInfo, `is`("Du musst einloggen, um diese Seite zu sehen."))
        return this
    }

    fun showsAuthModal(): ScreenApi {
        webDriver.findElement(By.cssSelector("div.auth0-lock-opened"))
        return this
    }

    fun showsHolidayOverview():ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Neuen Urlaub anlegen:"))
        return this
    }

    fun showsNoHolidays(): ScreenApi {
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        assertThat(rows.isEmpty(), `is`(true))
        return this
    }

    fun showsOneHoliday(): ScreenApi {
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        assertThat(rows.size, `is`(1))
        return this
    }

    fun showsHome(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Home"))
        return this
    }
}
