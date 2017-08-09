package org.mboyz.holidayplanner.holiday

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.User
import org.mboyz.holidayplanner.util.LocalDateDeserializer
import org.mboyz.holidayplanner.util.LocalDateSerializer
import java.time.LocalDate
import javax.persistence.*

@Entity
@JsonAutoDetect
data class Holiday @JvmOverloads constructor(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "holiday_id", updatable = false, nullable = false)
    var id: Long = 0,
    var name: String = "",
    var location: String = "",

    @Column(name = "start_date")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    var startDate: LocalDate? = null,

    @Column(name = "end_date")
    @JsonSerialize(using = LocalDateSerializer::class)
    @JsonDeserialize(using = LocalDateDeserializer::class)
    var endDate: LocalDate? = null,

    @OneToMany(
            mappedBy = "holiday",
            fetch = FetchType.EAGER,
            cascade = arrayOf(CascadeType.ALL),
            orphanRemoval = true)
    var participations: MutableSet<Participation> = mutableSetOf()
) {
    fun removeParticipation(user: User) {
        participations.removeIf { p -> p.user == user }
    }

    fun addParticipation(participation: Participation) {
        if (participations.any { it.user == participation.user }) return
        participations.add(participation)
    }
}