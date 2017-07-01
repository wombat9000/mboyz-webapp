package org.mboyz.holidayplanner.webdriver

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.After
import org.junit.AfterClass
import org.junit.BeforeClass
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.openqa.selenium.By
import org.openqa.selenium.Dimension
import org.openqa.selenium.JavascriptExecutor
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.phantomjs.PhantomJSDriver
import org.openqa.selenium.phantomjs.PhantomJSDriverService
import org.openqa.selenium.remote.DesiredCapabilities
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.context.embedded.LocalServerPort
import java.net.InetAddress
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit

abstract class AbstractWebdriverTest : AbstractSpringTest() {
    @Autowired
    fun initHolidayRepository(holidayRepository: HolidayRepository) {
        app = AppApi(holidayRepository)
    }

    companion object {
        lateinit var webDriver: WebDriver
        lateinit var screen: ScreenApi
        lateinit var user: UserApi
        lateinit var js: JsApi
        lateinit var app: AppApi

        @BeforeClass @JvmStatic fun setUp() {
//            webDriver = setupChromeDriver()
            webDriver = setupPhantomJSDriver()

            screen = ScreenApi(webDriver)
            user = UserApi(webDriver)
            js = JsApi(webDriver as JavascriptExecutor)
        }

        private fun setupChromeDriver(): WebDriver {
            System.setProperty("webdriver.chrome.driver", "/usr/local/bin/chromedriver")
            return ChromeDriver()
        }

        private fun setupPhantomJSDriver(): WebDriver {
            val caps = DesiredCapabilities()
            caps.isJavascriptEnabled = true
            caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, "node_modules/phantomjs-prebuilt/bin/phantomjs")

            val driver = PhantomJSDriver(caps)
            driver.manage().window().size = Dimension(1920, 1080)
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS)

            return driver
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

class AppApi (val holidayRepository: HolidayRepository){
    fun createHoliday(holiday: Holiday): AppApi {
        holidayRepository.save(holiday)
        return this
    }

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

    fun createsHoliday(holiday: Holiday): UserApi {
        webDriver.findElement(By.name("name")).sendKeys(holiday.name)
        webDriver.findElement(By.name("location")).sendKeys(holiday.location)
        webDriver.findElement(By.className("start-date-field")).sendKeys(holiday.startDate!!.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))).toString()
        webDriver.findElement(By.className("end-date-field")).sendKeys(holiday.endDate!!.format(DateTimeFormatter.ofPattern("dd.MM.YYYY"))).toString()

        // submit form
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
        // TODO add assertion once modal is not dependant on internet connection
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

    fun showsHoliday(holiday: Holiday): ScreenApi {
        // TODO:
        // this does not work, because it will always immediately find the table,
        // and not wait for rows to appear asynchronously.
        // As a workaround, we sleep 200millis to give the application time to render the rows,
        // before the webdriver attempts to fetch them.
        Thread.sleep(200)
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        val rowsAsText: List<String> = rows.map { row -> row.text }

        val expectedRow = "${holiday.name} ${holiday.location} ${holiday.startDate} ${holiday.endDate}"
        assertThat("row contains $expectedRow", rowsAsText.contains(expectedRow), `is`(true))
        return this
    }

    fun showsHome(): ScreenApi {
        val pageHeading = webDriver.findElement(By.tagName("h2")).text
        assertThat(pageHeading, `is`("Home"))
        return this
    }

    fun doesNotShowHoliday(holiday: Holiday): ScreenApi {
        // TODO:
        // this does not work, because it will always immediately find the table,
        // and not wait for rows to appear asynchronously.
        // As a workaround, we sleep 200millis to give the application time to render the rows,
        // before the webdriver attempts to fetch them.
        Thread.sleep(200)
        val rows = webDriver.findElements(By.cssSelector("table tbody tr"))
        val rowsAsText: List<String> = rows.map { row -> row.text }

        val expectedRow = "${holiday.name} ${holiday.location} ${holiday.startDate} ${holiday.endDate}"
        assertThat("row contains $expectedRow", rowsAsText.contains(expectedRow), `is`(false))
        return this
    }
}
