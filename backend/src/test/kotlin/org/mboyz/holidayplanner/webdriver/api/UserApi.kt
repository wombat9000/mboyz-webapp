package org.mboyz.holidayplanner.webdriver.api

import com.auth0.Tokens
import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.mboyz.holidayplanner.any
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.mboyz.holidayplanner.web.Auth0Client
import org.mboyz.holidayplanner.web.Auth0Wrapper
import org.mockito.BDDMockito.given
import org.openqa.selenium.By
import org.openqa.selenium.WebDriver
import java.time.format.DateTimeFormatter

class UserApi(val webDriver: WebDriver,
              val auth0ClientMock: Auth0Client,
              val auth0WrapperMock: Auth0Wrapper,
              val auth0Secret: String) {

    fun visits(url: String): UserApi {
        webDriver.navigate().to(url)
        return this
    }

    fun opensHolidayOverview(): UserApi {
        webDriver.findElement(By.cssSelector("nav ul li a[href='/holiday']"))
                .click()
        return this
    }

    fun loginAs(user: UserEntity): UserApi {
        given(auth0WrapperMock.buildAuthorizeUrl(any(), any())).willReturn("/auth0Test")
        given(auth0WrapperMock.handle(any())).willReturn(Tokens("someAccessToken", user.provideSignedToken(),"", "bearer", 9000))
        given(auth0ClientMock.getUserInfo("someAccessToken")).willReturn(user.getUserInfo())

        webDriver.findElement(By.cssSelector("ul.navbar-nav.navbar-right li.nav-item a.nav-link")).click()
        return this
    }

    fun createsHoliday(holiday: Holiday): UserApi {
        webDriver.findElement(By.name("name")).sendKeys(holiday.name)
        webDriver.findElement(By.name("location")).sendKeys(holiday.location)
        webDriver.findElement(By.name("startDate")).sendKeys(holiday.startDate!!.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"))).toString()
        webDriver.findElement(By.name("endDate")).sendKeys(holiday.endDate!!.format(DateTimeFormatter.ofPattern("MM/dd/YYYY"))).toString()

        webDriver.findElement(By.name("saveHoliday")).click()
        return this
    }

    fun clicksLogout(): UserApi {
        webDriver.findElement(By.cssSelector("ul.navbar-nav.navbar-right li.nav-item a.nav-link")).click()
        return this
    }

    fun navigatesToHolidaysCreation(): UserApi {
        webDriver.findElement(By.cssSelector("nav ul li a[href='/holiday']")).click()
        webDriver.findElement(By.cssSelector("p a[href='/holiday/create']")).click()
        return this
    }

    fun isLoggedIn(): UserApi {
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-nav.navbar-right li.nav-item a.nav-link")).getAttribute("text")
        assertThat(loginButtonText, `is`("Logout"))
        return this
    }

    fun isLoggedOut(): UserApi {
        val loginButtonText = webDriver.findElement(By.cssSelector("ul.navbar-nav.navbar-right li.nav-item a.nav-link")).getAttribute("text")
        assertThat(loginButtonText, `is`("Login"))
        return this
    }

    fun opensUserOverview(): UserApi {
        webDriver.findElement(By.cssSelector("nav ul li a[href='/user']"))
                .click()
        return this
    }

    private fun UserEntity.getUserInfo(): MutableMap<String, Any> {
        val userInfo = mutableMapOf<String, Any>()
        userInfo.put("given_name", this.givenName)
        userInfo.put("family_name", this.familyName)
        userInfo.put("picture", this.imageUrl)
        return userInfo
    }

    private fun UserEntity.provideSignedToken(): String {
        return JWT.create()
                .withSubject("facebook|${this.fbId}")
                .withAudience("someAudience")
                .withIssuer("https://wombat9000.eu.auth0.com/")
                .sign(Algorithm.HMAC256(auth0Secret))

    }
}



