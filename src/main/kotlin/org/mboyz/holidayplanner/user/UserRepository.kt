package org.mboyz.holidayplanner.user

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

@Repository
@Transactional
interface UserRepository: JpaRepository<User, Long> {
    fun findByFbId(fbId: String): User?
}