package org.mboyz.holidayplanner.holiday

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface HolidayRepository: CrudRepository<Holiday, Long>