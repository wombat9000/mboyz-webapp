package org.mboyz.holidayplanner.user

import org.springframework.security.crypto.bcrypt.BCrypt

data class UnpersistedUser(val firstName: String,
                           val lastName: String,
                           val email: String,
                           val password: String) {

    val salt: String = BCrypt.gensalt()
    val pwHash: String =  BCrypt.hashpw(password, salt)
}