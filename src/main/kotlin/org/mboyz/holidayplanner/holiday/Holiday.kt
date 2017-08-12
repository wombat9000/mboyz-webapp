package org.mboyz.holidayplanner.holiday

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.User
import java.time.LocalDate
import javax.persistence.*

@Entity
data class Holiday @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", updatable = false, nullable = false)
    var id: Long = 0,
    var name: String = "",
    var location: String = "",

    @Column(name = "start_date")
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    var endDate: LocalDate? = null,

    @OneToMany(
            mappedBy = "holiday",
            fetch = FetchType.EAGER,
            cascade = arrayOf(CascadeType.ALL),
            orphanRemoval = true)
    var participations: MutableSet<Participation> = mutableSetOf(),

    @OneToMany(
            mappedBy = "holiday",
            fetch = FetchType.EAGER,
            cascade = arrayOf(CascadeType.ALL),
            orphanRemoval = true)
    var comments: MutableList<Comment> = mutableListOf()

) {
    // used in holiday detail
    fun commentsSorted() {
        return comments.sortBy { x -> x.created }
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