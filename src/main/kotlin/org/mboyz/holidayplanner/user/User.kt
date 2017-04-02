package org.mboyz.holidayplanner.user

import java.io.Serializable

data class User(val id: Int?,
                val firstName: String,
                val lastName: String,
                val email: String,
                val passwordHash: String,
                val passwordSalt: String): Serializable {

    companion object {
        fun unpersistedUser(firstName: String,
                            lastName: String,
                            email: String,
                            passwordHash: String,
                            passwordSalt: String): User {
            return User(null, firstName, lastName, email, passwordHash, passwordSalt)
        }
    }
}