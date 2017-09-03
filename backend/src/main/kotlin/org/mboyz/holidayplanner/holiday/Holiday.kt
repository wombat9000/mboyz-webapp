package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.persistence.UserEntity
import java.time.LocalDate

data class Holiday(
        val id: Long,
        val name: String,
        val location: String,
        val startDate: LocalDate,
        val endDate: LocalDate,
        val deletingUser: UserEntity,
        val participations: Set<Participation>,
        val comments: List<Comment>)