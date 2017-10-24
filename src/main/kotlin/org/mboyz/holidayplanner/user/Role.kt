package org.mboyz.holidayplanner.user

data class Role(
     val id: Long,
     val name: String,
     val users: Set<User>,
     val privileges: Set<Privilege>
)