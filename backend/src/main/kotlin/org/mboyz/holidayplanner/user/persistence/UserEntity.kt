package org.mboyz.holidayplanner.user.persistence

import org.mboyz.holidayplanner.holiday.Comment
import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.participation.Participation
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
        var participations: MutableSet<Participation> = mutableSetOf(),

        @OneToMany(mappedBy = "user", fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL), orphanRemoval = true)
        var comments: MutableSet<Comment> = mutableSetOf()) {

    fun removeParticipation(holiday: Holiday) {
        participations.removeIf { p -> p.holiday == holiday }
    }

    fun addParticipation(participation: Participation) {
        if (participations.any { it.holiday == participation.holiday }) return
        participations.add(participation)
    }

    fun addComment(comment: Comment) {
        comments.add(comment)
    }
}
