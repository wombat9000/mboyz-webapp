package org.mboyz.holidayplanner.user

data class Privilege(
        val id: Long,
        val name: String,
        val roles: Set<Role>
)