package org.mboyz.holidayplanner.web

import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.security.authentication.AbstractAuthenticationToken
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.util.*

class TokenAuthentication(private val jwt: DecodedJWT) : AbstractAuthenticationToken(readAuthorities(jwt)) {

    companion object {
        private fun readAuthorities(jwt: DecodedJWT): Collection<GrantedAuthority> {
            val rolesClaim = jwt.getClaim("https://access.control/roles")
            if (rolesClaim.isNull) {
                return emptyList()
            }
            val authorities = ArrayList<GrantedAuthority>()
            val scopes = rolesClaim.asArray(String::class.java)
            scopes
                    .map { SimpleGrantedAuthority(it) }
                    .filterNot { authorities.contains(it) }
                    .forEach { authorities.add(it) }
            return authorities
        }
    }

    private var invalidated: Boolean = false

    private fun hasExpired(): Boolean {
        return jwt.expiresAt.before(Date())
    }

    override fun getCredentials(): String {
        return jwt.token
    }

    override fun getPrincipal(): Any {
        return jwt.subject
    }

    override fun setAuthenticated(authenticated: Boolean) {
        if (authenticated) {
            throw IllegalArgumentException("Create a new Authentication object to authenticate")
        }
        invalidated = true
    }

    override fun isAuthenticated(): Boolean {
        return !invalidated && !hasExpired()
    }
}