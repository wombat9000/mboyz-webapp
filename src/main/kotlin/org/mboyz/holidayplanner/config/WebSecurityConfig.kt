package org.mboyz.holidayplanner.config

import com.auth0.AuthenticationController
import com.auth0.client.auth.AuthAPI
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import java.io.UnsupportedEncodingException

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    companion object {
        val HOME = "/"
        val LOGIN = "/login"
        val CALLBACK = "/callback"
        val WEBJARS = "/webjars/**"
        val ALL = "/**"
    }

    @Suppress("unused")
    val secretFromEnv: String? = System.getenv("AUTH0_SECRET")
    @Value("\${auth0.secret:secretFromEnv}")
    lateinit var AUTH0_SECRET: String
    @Value(value = "\${com.auth0.domain}")
    private val domain: String? = null
    @Value(value = "\${com.auth0.clientId}")
    private val clientId: String? = null

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http.csrf().disable()
        http.authorizeRequests()
                .antMatchers(HOME, CALLBACK, LOGIN, WEBJARS, "/auth0/*", "/auth0Test", "/favicon.ico").permitAll()
                .antMatchers(ALL).authenticated()
                .and()
                .logout().logoutSuccessUrl(HOME).permitAll()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
    }

    @Bean
    @Profile("prod")
    @Throws(UnsupportedEncodingException::class)
    fun auth0Controller(): AuthenticationController {
        return AuthenticationController.newBuilder(domain, clientId, AUTH0_SECRET)
                .withResponseType("code")
                .build()
    }

    @Bean
    @Profile("prod")
    fun auth0Api(): AuthAPI {
        return AuthAPI(domain, clientId, AUTH0_SECRET)
    }
}