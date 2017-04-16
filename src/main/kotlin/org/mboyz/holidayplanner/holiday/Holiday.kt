package org.mboyz.holidayplanner.holiday

import javax.persistence.*

@Entity
class Holiday (@Id
               @GeneratedValue(strategy = GenerationType.IDENTITY)
               @Column(name = "holiday_id")
               val id: Long = 0,
               val name: String = "")
