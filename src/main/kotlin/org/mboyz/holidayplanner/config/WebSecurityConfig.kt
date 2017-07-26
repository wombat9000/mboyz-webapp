package org.mboyz.holidayplanner.config

import com.auth0.AuthenticationController
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import java.io.UnsupportedEncodingException

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = false, securedEnabled = true)
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

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
                .antMatchers("/callback", "/login", "/", "/webjars/**").permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .logout().permitAll()
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.NEVER)
    }

    @Bean
    @Throws(UnsupportedEncodingException::class)
    fun authenticationController(): AuthenticationController {
        return AuthenticationController.newBuilder(domain, clientId, AUTH0_SECRET)
                .withResponseType("code")
                .build()
    }
}