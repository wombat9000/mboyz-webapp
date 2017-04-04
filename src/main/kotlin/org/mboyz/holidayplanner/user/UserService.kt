package org.mboyz.holidayplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService (@Autowired val userMapper: UserMapper){
    fun persist(unpersistedUser: UnpersistedUser) {
        userMapper.insert(unpersistedUser)
    }
}