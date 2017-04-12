package org.mboyz.holidayplanner.holiday

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
class Holiday (@Id
               @GeneratedValue(strategy= GenerationType.AUTO)
               val id: Long,
               val name: String)