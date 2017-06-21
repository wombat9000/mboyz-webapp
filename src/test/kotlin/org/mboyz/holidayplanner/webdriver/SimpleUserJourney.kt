package org.mboyz.holidayplanner.webdriver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Test
import org.mboyz.holidayplanner.holiday.Holiday
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource
import java.time.LocalDate

@PropertySource("classpath:secret.properties", ignoreResourceNotFound = true)
class SimpleUserJourney : AbstractWebdriverTest(){

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        val someHoliday = Holiday(
                1L,
                "Surfurlaub",
                "Frankreich",
                LocalDate.parse("2017-05-28"),
                LocalDate.parse("2017-05-30"))

        user    .visits(HOME)
        screen  .showsHome()
                .showsLoginButton()
        user    .navigatesToHolidaysPage()
        screen  .showsUnauthInfo()
        user    .clicksLogin()
        screen  .showsAuthModal()

        // "login" by generating valid token, as opposed to retrieving one through Auth0
        js      .setLocalStorage("id_token", generateSignedToken())

        user    .navigatesToHolidaysPage()
        screen  .showsHolidayOverview()
                .showsNoHolidays()

        // TODO: create holiday via admin

        // TODO: it shows initially existing holidays
        user    .createsHoliday(someHoliday)

        // TODO: create holiday with invalid date

        screen  .showsHoliday(someHoliday)

        // TODO: visits holiday detail page

        user    .clicksLogout()
        screen  .showsHome()
                .showsLoginButton()
    }

    fun generateSignedToken(): String {
        return JWT.create()
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(Algorithm.HMAC256(AUTH0_SECRET))
    }
}
