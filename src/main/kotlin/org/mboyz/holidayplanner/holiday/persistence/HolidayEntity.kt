package org.mboyz.holidayplanner.holiday.persistence

import org.mboyz.holidayplanner.user.persistence.UserEntity
import java.time.LocalDate
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "holiday")
data class HolidayEntity
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
        @ManyToOne @JoinColumn(name = "deleting_user_id")
        var deletingUser: UserEntity? = null,

        @OneToMany(mappedBy = "holiday", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var participations: MutableSet<ParticipationEntity> = mutableSetOf(),

        @OneToMany(mappedBy = "holiday", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var comments: MutableList<CommentEntity> = mutableListOf()) {

    // used in holiday detail template
    fun commentsSorted() {
        return comments.sortBy { x -> x.created }
    }

    fun notDeleted(): Boolean {
        return deletedDate == null
    }

    fun removeParticipation(user: UserEntity) {
        participations.removeIf { p -> p.user == user }
    }

    fun addParticipation(participation: ParticipationEntity) {
        if (participations.any { it.user == participation.user }) return
        participations.add(participation)
    }

    fun addComment(comment: CommentEntity) {
        comments.add(comment)
    }

    fun asHolidayDetail(): HolidayDetail {
        val participants = participations.map { Participant(it.user!!.id, it.user!!.givenName, it.user!!.imageUrl) }.toSet()

        val comments: List<Comment> = comments.map { Comment(it.createdFormatted(), it.text, it.user!!.givenName) }

        return HolidayDetail(id, name, location, startDate, endDate, participants, comments)
    }
}

data class HolidayDetail(val id: Long, val name: String, val location: String, val startDate: LocalDate?, val endDate: LocalDate?, val participants: Set<Participant>, val comments: List<Comment>)
data class Participant(val id: Long, val name: String, val imageUrl: String)
data class Comment(val created: String, val text: String, val author: String)