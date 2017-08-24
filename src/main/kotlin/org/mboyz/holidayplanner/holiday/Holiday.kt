package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.User
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
data class Holiday
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "holiday_id", updatable = false, nullable = false)
        var id: Long = 0,
        var name: String = "",
        var location: String = "",
        @Column(name = "start_date")
        var startDate: LocalDate? = null,
        @Column(name = "end_date")
        var endDate: LocalDate? = null,
        @Column(name = "deleted_at")
        var deletedDate: LocalDateTime? = null,
        @ManyToOne() @JoinColumn(name = "deleting_user_id")
        var deletingUser: User? = null,

        @OneToMany(mappedBy = "holiday", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var participations: MutableSet<Participation> = mutableSetOf(),

        @OneToMany(mappedBy = "holiday", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var comments: MutableList<Comment> = mutableListOf()) {
    // used in holiday detail template
    fun commentsSorted() {
        return comments.sortBy { x -> x.created }
    }

    fun notDeleted(): Boolean {
        return deletedDate == null
    }

    fun removeParticipation(user: User) {
        participations.removeIf { p -> p.user == user }
    }

    fun addParticipation(participation: Participation) {
        if (participations.any { it.user == participation.user }) return
        participations.add(participation)
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}