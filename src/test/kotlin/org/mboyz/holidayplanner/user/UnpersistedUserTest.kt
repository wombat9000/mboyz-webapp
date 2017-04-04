package org.mboyz.holidayplanner.user

import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.not
import org.junit.Assert.*
import org.junit.Test
import org.springframework.security.crypto.bcrypt.BCrypt

class UnpersistedUserTest {

    @Test
    fun itShouldHashPw() {
        val somePW = "somePW"
        val unpersistedUser = UnpersistedUser("someFirst", "bla", "bla", somePW)

        val pwHash = unpersistedUser.pwHash
        val pwChecksOut: Boolean = BCrypt.checkpw(somePW, pwHash)

        assertThat(somePW, not(pwHash))
        assertThat(pwChecksOut, `is`(true))
    }
}