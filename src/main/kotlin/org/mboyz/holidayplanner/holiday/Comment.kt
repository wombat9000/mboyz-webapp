package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.user.User
import java.time.LocalDateTime
import java.time.ZoneId
import javax.persistence.*

@Entity
data class Comment @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "comment_id", updatable = false, nullable = false)
        var id: Long = 0L,

        @ManyToOne
        @JoinColumn(name = "holiday_id")
        var holiday: Holiday? = Holiday(),

        @ManyToOne()
        @JoinColumn(name = "user_id")
        var user: User? = User(),

        var text: String? = "",

        var created: LocalDateTime = LocalDateTime.now(ZoneId.of(EUROPEAN_CENTRAL_TIME))
) {
    companion object {
        val EUROPEAN_CENTRAL_TIME = "Europe/Paris"
    }

    override fun toString(): String {
        return "Comment(id=$id, holiday=${holiday?.id?: ""}, user=${user?.id?: ""}, text=$text, created=$created)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as Comment

        if (id != other.id) return false
        if ((holiday != null && other.holiday != null) && holiday!!.id != other.holiday!!.id) return false
        if ((user != null && other.user != null) && user!!.id != other.user!!.id) return false
        if (text != other.text) return false
        if (created != other.created) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + (holiday?.id?.hashCode()?: 0)
        result = 31 * result + (user?.id?.hashCode()?: 0)
        result = 31 * result + (text?.hashCode() ?: 0)
        result = 31 * result + created.hashCode()
        return result
    }

}