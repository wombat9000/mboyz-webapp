package org.mboyz.holidayplanner.holiday.participation

import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.user.User
import javax.persistence.*

@Entity
data class Participation
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "participation_id", updatable = false, nullable = false)
        var id: Long = 0L,

        @ManyToOne @JoinColumn(name = "holiday_id")
        var holiday: Holiday? = Holiday(),

        @ManyToOne() @JoinColumn(name = "user_id")
        var user: User? = User()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Participation

        if (id != other.id) return false
        if ((holiday != null && other.holiday != null) && holiday!!.id != other.holiday!!.id) return false
        if ((user != null && other.user != null) && user!!.id != other.user!!.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (holiday?.id?.hashCode() ?: 0)
        result = 31 * result + (user?.id?.hashCode() ?: 0)
        return result
    }

    override fun toString(): String {
        return "Participation(id=$id, holiday=${holiday?.id ?: ""}, user=${user?.id ?: ""})"
    }
}