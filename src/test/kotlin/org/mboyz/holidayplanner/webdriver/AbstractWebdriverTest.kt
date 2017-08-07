package org.mboyz.holidayplanner.webdriver

import org.junit.After
import org.junit.AfterClass
import org.junit.Before
import org.junit.BeforeClass
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.mboyz.holidayplanner.user.UserRepository
import org.mboyz.holidayplanner.web.Auth0Client
import org.mboyz.holidayplanner.web.Auth0Wrapper
import org.mboyz.holidayplanner.webdriver.api.AppApi
import org.mboyz.holidayplanner.webdriver.api.ScreenApi
import org.mboyz.holidayplanner.webdriver.api.UserApi
import org.openqa.selenium.WebDriver
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.embedded.LocalServerPort
import org.springframework.boot.test.mock.mockito.MockBean
import java.net.InetAddress

abstract class AbstractWebdriverTest : AbstractSpringTest() {

    companion object {
        lateinit var webDriver: WebDriver

        @BeforeClass @JvmStatic fun setUp() {
            webDriver = setupChromeDriver()
        }

        @AfterClass @JvmStatic fun cleanUp() {
            webDriver.quit()
        }

        private fun setupChromeDriver(): WebDriver {
            val chromeOptions = ChromeOptions()
            chromeOptions.addArguments("--window-size=1920,1080")
//            chromeOptions.addArguments("--headless")
            System.setProperty("webdriver.chrome.driver", "node_modules/chromedriver/bin/chromedriver")
            return ChromeDriver(chromeOptions)
        }
    }

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String
    lateinit var screen: ScreenApi
    lateinit var user: UserApi
    lateinit var app: AppApi

    @Autowired
    fun initAppApi(holidayRepository: HolidayRepository, userRepository: UserRepository) {
        app = AppApi(holidayRepository, userRepository)
    }

    @MockBean
    lateinit var auth0Mock: Auth0Wrapper
    @MockBean
    lateinit var auth0Client: Auth0Client

    @Before override fun setup() {
        super.setup()
        user = UserApi(webDriver, auth0Client, auth0Mock, AUTH0_SECRET)
        screen = ScreenApi(webDriver)
    }

    @After override fun tearDown() {
        super.tearDown()
        webDriver.close()
    }

    @LocalServerPort var port: Int? = null
    var contextPath: String? = InetAddress.getLocalHost().hostAddress
}
