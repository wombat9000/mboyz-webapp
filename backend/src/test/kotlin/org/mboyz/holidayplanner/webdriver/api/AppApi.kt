package org.mboyz.holidayplanner.webdriver.api

import org.mboyz.holidayplanner.holiday.Holiday
import org.mboyz.holidayplanner.holiday.HolidayRepository
import org.mboyz.holidayplanner.user.persistence.UserRepository

class AppApi (val holidayRepository: HolidayRepository, val userRepository: UserRepository){
    fun deleteAllHolidays(): AppApi {
        holidayRepository.deleteAll()
        return this
    }

    fun createHoliday(holiday: Holiday): AppApi {
        holidayRepository.save(holiday)
        return this
    }

    fun deleteAllUsers(): AppApi {
        userRepository.deleteAll()
        return this
    }
}