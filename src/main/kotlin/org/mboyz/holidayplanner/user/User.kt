package org.mboyz.holidayplanner.user

data class User(val id: Int?,
                val firstName: String,
                val lastName: String,
                val email: String,
                val passwordHash: String)