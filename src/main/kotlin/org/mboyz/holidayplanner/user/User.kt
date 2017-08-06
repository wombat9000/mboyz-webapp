package org.mboyz.holidayplanner.user

import org.mboyz.holidayplanner.holiday.participation.Participation
import javax.persistence.*

@Entity
@Table(name = "\"user\"")
data class User @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", updatable = false, nullable = false)
        var id: Long = 0,

        @Column(name = "fb_id")
        var fbId: String = "",

        @Column(name = "given_name")
        var givenName: String = "",

        @Column(name = "family_name")
        var familyName: String = "",

        @Column(name = "image_url")
        var imageUrl: String = "",

        @OneToMany(
                mappedBy = "user",
                fetch = FetchType.EAGER,
                cascade = arrayOf(CascadeType.ALL),
                orphanRemoval = true)
        var participations: MutableSet<Participation> = mutableSetOf()
) {
}
