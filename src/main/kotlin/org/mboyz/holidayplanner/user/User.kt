package org.mboyz.holidayplanner.user

import javax.persistence.*

@Entity
@Table(name = "\"user\"")
data class User @JvmOverloads constructor(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "user_id", updatable = false, nullable = false)
        var id: Long = 0,

        @Column(name = "fb_id")
        var fbId: String = ""
)