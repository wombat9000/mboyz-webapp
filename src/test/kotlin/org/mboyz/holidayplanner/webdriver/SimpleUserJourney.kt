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

    companion object {
        val SURF_HOLIDAY = Holiday(
                name = "Surfurlaub",
                location = "Frankreich",
                startDate = LocalDate.parse("2017-05-28"),
                endDate = LocalDate.parse("2017-05-30"))

        val SKI_HOLIDAY = Holiday(
                name = "Skiurlaub",
                location = "Portes du Soleil",
                startDate = LocalDate.parse("2017-03-28"),
                endDate = LocalDate.parse("2017-04-05"))

        val HOLIDAY_WITH_INVALID_DATE = Holiday(
                name = "Some Name",
                location = "Some Location",
                startDate = LocalDate.parse("2017-04-05"),
                endDate = LocalDate.parse("2017-03-28"))
    }

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

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

        app     .createHoliday(SKI_HOLIDAY)

        user    .visits(HOME)
                .navigatesToHolidaysPage()
        screen  .showsHoliday(SKI_HOLIDAY)

        user    .createsHoliday(SURF_HOLIDAY)
        screen  .showsHoliday(SURF_HOLIDAY)

        user    .createsHoliday(HOLIDAY_WITH_INVALID_DATE)
        screen  .doesNotShowHoliday(HOLIDAY_WITH_INVALID_DATE)

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
