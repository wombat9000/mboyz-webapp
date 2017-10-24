package org.mboyz.holidayplanner.user

import org.mboyz.holidayplanner.holiday.Participation

data class User (
        val id: Long,
        val fbId: String,
        val roles: Set<Role>,
        val givenName: String,
        val familyName: String,
        val imageUrl: String,
        val participations: Set<Participation>) {
}