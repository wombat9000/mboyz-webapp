package org.mboyz.holidayplanner.holiday

import com.fasterxml.jackson.annotation.JsonAutoDetect
import javax.persistence.*

@Entity
@JsonAutoDetect
data class Holiday (@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    @Column(name = "holiday_id")
                    val id: Long = 0,
                    val name: String = "",
                    val location: String = "")
