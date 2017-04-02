package org.mboyz.holidayplanner.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.security.crypto.bcrypt.BCrypt
import org.springframework.stereotype.Service

@Service
class UserService (@Autowired val userMapper: UserMapper){
    fun create(firstName: String, lastName: String, email: String, password: String) {
        val salt: String = BCrypt.gensalt()
        val pwHash: String = BCrypt.hashpw(password, salt)
        val unpersistedUser = User.unpersistedUser(firstName, lastName, email, pwHash, salt)

        userMapper.insert(unpersistedUser)
    }
}