package org.mboyz.holidayplanner.user

import org.mboyz.holidayplanner.holiday.participation.Participation
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.mboyz.holidayplanner.user.persistence.UserRepository
import org.mboyz.holidayplanner.web.Auth0Client
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional


@Component
@Transactional
class UserService
@Autowired
constructor(val userRepository: UserRepository, val auth0Client: Auth0Client) {

    fun createOrUpdate(fbId: String, accessToken: String): UserEntity? {
        return userRepository.findByFbId(fbId) ?: createNewUserWithFieldsFromAuth0(fbId, accessToken)
    }

    private fun createNewUserWithFieldsFromAuth0(fbId: String, accessToken: String): UserEntity? {
        val userInfo = auth0Client.getUserInfo(accessToken)
        val userToCreate = UserEntity(
                fbId = fbId,
                givenName = userInfo["given_name"] as String,
                familyName = userInfo["family_name"] as String,
                imageUrl = userInfo["picture"] as String
        )
        return userRepository.save(userToCreate)
    }

    fun findAll(): Iterable<UserEntity> {
        return userRepository.findAll()
    }

    fun findOne(id: Long): UserEntity? {
        return userRepository.findOne(id)
    }

    fun deleteAll() {
        userRepository.findAll().forEach(UserEntity::clearParticipations)
        userRepository.deleteAll()
    }

    fun save(user: UserEntity): UserEntity {
        return userRepository.save(user)!!
    }

    fun findByFbId(fbId: String): UserEntity? {
        return userRepository.findByFbId(fbId)
    }
}

private fun UserEntity.clearParticipations() {
    this.participations.forEach(Participation::removeReferenceToUser)
    this.participations.clear()
}

private fun Participation.removeReferenceToUser() {
    this.user = null
}