package org.mboyz.holidayplanner.user

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class UserServiceTest {

    lateinit var testee: UserService

    @Mock
    lateinit var userRepositoryMock: UserRepository

    @Before
    fun setUp() {
    	initMocks(this)
    	testee = UserService(userRepositoryMock)
    }

    @Test
    fun shouldCreateUser() {
        val fbId = "someFbId"
        val userToCreate = User(fbId = fbId)
        val persistedUser = User(1, fbId)
        given(userRepositoryMock.findByFbId(fbId)).willReturn(null)
    	given(userRepositoryMock.save(userToCreate)).willReturn(persistedUser)

        val result = testee.findOrCreate(fbId)

        assertThat(result, `is`(persistedUser))
    }

    @Test
    fun shouldNotCreateUserIfAlreadyExists() {
        val fbId = "someFbId"
        val userToCreate = User(fbId = fbId)
        val persistedUser = User(1, fbId)
        given(userRepositoryMock.findByFbId(fbId)).willReturn(persistedUser)

        val result = testee.findOrCreate(fbId)

        verify(userRepositoryMock, never()).save(userToCreate)
        assertThat(result, `is`(persistedUser))
    }
}
