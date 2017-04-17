package org.mboyz.holidayplanner.holiday

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import org.mboyz.holidayplanner.util.LocalDateDeserializer
import org.mboyz.holidayplanner.util.LocalDateSerializer
import java.time.LocalDate
import javax.persistence.*

@Entity
@JsonAutoDetect
data class Holiday @JvmOverloads constructor(@Id
                    @GeneratedValue(strategy = GenerationType.IDENTITY)
                    @Column(name = "holiday_id", updatable = false, nullable = false)
                    val id: Long = 0,
                    val name: String = "",
                    val location: String = "",
                    @Column(name = "start_date")
                    @JsonSerialize(using = LocalDateSerializer::class)
                    @JsonDeserialize(using = LocalDateDeserializer::class)
                    val startDate: LocalDate? = null,
                    @Column(name = "end_date")
                    @JsonSerialize(using = LocalDateSerializer::class)
                    @JsonDeserialize(using = LocalDateDeserializer::class)
                    val endDate: LocalDate? = null)