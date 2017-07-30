package org.mboyz.holidayplanner.user
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.After
import org.junit.Test
import org.mboyz.holidayplanner.integration.AbstractSpringTest
import org.springframework.beans.factory.annotation.Autowired

class UserPersistenceTest: AbstractSpringTest() {

    @Autowired
    lateinit var testee: UserRepository

    @After
    fun setUp() {
    	testee.deleteAll()
    }

    @Test
    fun shouldFindByFbId() {
        testee.save(User(fbId = "someFbID"))

        val result = testee.findByFbId("someFbID")!!

        assertThat(result.fbId, `is`("someFbID"))
    }

    @Test
    fun shouldReturnNullIfCantFindFbId() {
        val result = testee.findByFbId("someFbID")
        assertThat(result, `is`(nullValue()))
    }
}