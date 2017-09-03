package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.User


data class Participation(
        val id: Long,
        val holiday: Holiday,
        val user: User)