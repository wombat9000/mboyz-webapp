package org.mboyz.holidayplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class UserService (@Autowired val userRepository: UserRepository){
    fun findOrCreate(fbId: String): User? {
        return userRepository.findByFbId(fbId) ?: userRepository.save(User(fbId = fbId))
    }
}