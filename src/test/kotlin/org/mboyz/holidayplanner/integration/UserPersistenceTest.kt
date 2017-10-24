package org.mboyz.holidayplanner.integration
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Test
import org.mboyz.holidayplanner.user.persistence.UserEntity
import org.mboyz.holidayplanner.user.persistence.UserRepository
import org.springframework.beans.factory.annotation.Autowired

// TODO: Make use of this annotation for faster tests?
//@DataJpaTest
class UserPersistenceTest: AbstractSpringTest() {
// TODO: figure out how to write meaningful persistence tests

    @Autowired
    lateinit var userRepository: UserRepository

    @Test
    fun shouldFindByFbId() {
        userRepository.save(UserEntity(fbId = "someFbID"))

        val result = userRepository.findByFbId("someFbID")!!

        assertThat(result.fbId, `is`("someFbID"))
    }

    @Test
    fun shouldReturnNullIfCantFindFbId() {
        val result = userRepository.findByFbId("someFbID")
        assertThat(result, `is`(nullValue()))
    }
}