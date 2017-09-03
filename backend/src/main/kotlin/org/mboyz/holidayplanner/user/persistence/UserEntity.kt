package org.mboyz.holidayplanner.user.persistence

import org.mboyz.holidayplanner.holiday.persistence.CommentEntity
import org.mboyz.holidayplanner.holiday.persistence.HolidayEntity
import org.mboyz.holidayplanner.holiday.persistence.ParticipationEntity
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
data class UserEntity
@JvmOverloads
constructor(
        @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", updatable = false, nullable = false)
        var id: Long = 0,
        @Column(name = "fb_id")
        var fbId: String = "",

        @ManyToMany(fetch = FetchType.EAGER)
        @JoinTable(
                name = "user_role",
                joinColumns = arrayOf(JoinColumn(name = "user_id", referencedColumnName = "user_id")),
                inverseJoinColumns = arrayOf(JoinColumn(name = "role_id", referencedColumnName = "role_id")))
        var roles: MutableSet<RoleEntity> = mutableSetOf(),
        @Column(name = "given_name")
        var givenName: String = "",
        @Column(name = "family_name")
        var familyName: String = "",
        @Column(name = "image_url")
        var imageUrl: String = "",
        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var participations: MutableSet<ParticipationEntity> = mutableSetOf(),

        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var comments: MutableSet<CommentEntity> = mutableSetOf()) {

    fun removeParticipation(holiday: HolidayEntity) {
        participations.removeIf { p -> p.holiday == holiday }
    }

    fun addParticipation(participation: ParticipationEntity) {
        if (participations.any { it.holiday == participation.holiday }) return
        participations.add(participation)
    }

    fun addComment(comment: CommentEntity) {
        comments.add(comment)
    }
}
