package org.mboyz.holidayplanner.webdriver

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.junit.Test

class SimpleUserJourney : AbstractWebdriverTest(){

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        user    .visit(HOME)
        screen  .showsLoginButton()
        user    .navigateToHolidaysPage()
        screen  .showsUnauthInfo()
        user    .clicksLogin()
        screen  .showsAuthModal()

        // "login" by generating valid token, as opposed to retrieving one through Auth0
        val token: String = generateToken()
        js      .setLocalStorage("id_token", token)

        user    .navigateToHolidaysPage()
        screen  .showsHolidayOverview()

        // visits urlaub page

        // sees no urlaube

        // creates one urlaub

        // sees one urlaub

        // logs out
    }

    fun generateToken(): String {
        val secret: String = System.getenv("AUTH0_SECRET")

        val algorithmHS = Algorithm.HMAC256(secret)

        return JWT.create()
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(algorithmHS)
    }
}