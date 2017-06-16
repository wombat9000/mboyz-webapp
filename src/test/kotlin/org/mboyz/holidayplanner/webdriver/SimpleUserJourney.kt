package org.mboyz.holidayplanner.webdriver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Test
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.PropertySource

@PropertySource("classpath:secret.properties")
class SimpleUserJourney : AbstractWebdriverTest(){

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        user    .visits(HOME)
        screen  .showsLoginButton()
        user    .navigatesToHolidaysPage()
        screen  .showsUnauthInfo()
        user    .clicksLogin()
        screen  .showsAuthModal()

        // "login" by generating valid token, as opposed to retrieving one through Auth0
        val token: String = generateToken()
        js      .setLocalStorage("id_token", token)

        user    .navigatesToHolidaysPage()
        screen  .showsHolidayOverview()
                .showsNoHolidays()

        // creates one urlaub

        // sees one urlaub

        // logs out
    }

    fun generateToken(): String {
        val algorithmHS = Algorithm.HMAC256(AUTH0_SECRET)

        return JWT.create()
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(algorithmHS)
    }
}