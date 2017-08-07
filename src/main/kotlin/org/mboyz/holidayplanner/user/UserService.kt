package org.mboyz.holidayplanner.user

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.web.Auth0Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
class UserService (@Autowired val userRepository: UserRepository,
                   @Autowired val auth0Client: Auth0Client){

    fun createOrUpdate(fbId: String, accessToken: String): User? {
        return userRepository.findByFbId(fbId) ?: createNewUserWithFieldsFromAuth0(fbId, accessToken)
    }

    private fun createNewUserWithFieldsFromAuth0(fbId: String, accessToken: String): User? {
        val userInfo = auth0Client.getUserInfo(accessToken)
        val userToCreate = User(
                fbId = fbId,
                givenName = userInfo["given_name"] as String,
                familyName = userInfo["family_name"] as String,
                imageUrl = userInfo["picture"] as String
        )
        return userRepository.save(userToCreate)
    }

    fun findAll(): Iterable<User> {
        return userRepository.findAll()
    }

    fun findOne(id: Long): User? {
        return userRepository.findOne(id)
    }

    @Transactional
    fun deleteAll() {
        userRepository.findAll().forEach(User::clearParticipations)
        userRepository.deleteAll()
    }

    fun save(user: User): User {
        return userRepository.save(user)!!
    }

    fun findByFbId(fbId: String): User? {
        return userRepository.findByFbId(fbId)
    }
}

private fun User.clearParticipations() {
    this.participations.forEach(Participation::removeReferenceToUser)
    this.participations.clear()
}

private fun Participation.removeReferenceToUser() {
    this.user = null
}