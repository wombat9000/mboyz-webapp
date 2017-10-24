package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.User
import java.time.LocalDateTime

data class Comment (
        val id: Long,
        val holiday: Holiday,
        val user: User,
        val text: String,
        val created: LocalDateTime)
