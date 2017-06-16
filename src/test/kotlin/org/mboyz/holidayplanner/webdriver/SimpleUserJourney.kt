package org.mboyz.holidayplanner.webdriver

import org.junit.Test

class SimpleUserJourney : AbstractWebdriverTest(){

    @Test
    fun simpleUserJourney() {
        val BASE_URL = "http://$contextPath:$port"
        val HOME = BASE_URL

        user
                .visit(HOME)
        screen
                .showsLoginButton()
        user
                .navigateToHolidaysPage()
        screen
                .showsUnauthInfo()
        user
                .clicksLogin()
        screen
                .showsAuthModal()

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