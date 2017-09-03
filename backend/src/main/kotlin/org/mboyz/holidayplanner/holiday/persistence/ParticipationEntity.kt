package org.mboyz.holidayplanner.holiday.persistence

import org.mboyz.holidayplanner.user.persistence.UserEntity
import javax.persistence.*

@Entity
@Table(name = "participation")
data class ParticipationEntity
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "participation_id", updatable = false, nullable = false)
        var id: Long = 0L,

        @ManyToOne @JoinColumn(name = "holiday_id")
        var holiday: HolidayEntity? = HolidayEntity(),

        @ManyToOne() @JoinColumn(name = "user_id")
        var user: UserEntity? = UserEntity()) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as ParticipationEntity

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