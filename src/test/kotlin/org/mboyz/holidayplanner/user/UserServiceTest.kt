package org.mboyz.holidayplanner.user

import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.web.Auth0Client
import org.mockito.BDDMockito.given
import org.mockito.BDDMockito.verify
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations.initMocks

class UserServiceTest {

    lateinit var testee: UserService

    @Mock
    lateinit var userRepositoryMock: UserRepository
    @Mock
    lateinit var auth0ClientMock: Auth0Client

    @Before
    fun setUp() {
    	initMocks(this)
    	testee = UserService(userRepositoryMock, auth0ClientMock)
    }

    @Test
    fun shouldNotCreateUserIfAlreadyExists() {
        val fbId = "someFbId"
        val userToCreate = User(fbId = fbId)
        val persistedUser = User(1, fbId)
        given(userRepositoryMock.findByFbId(fbId)).willReturn(persistedUser)

        val result = testee.createOrUpdate(fbId, "someAccessToken")

        verify(userRepositoryMock, never()).save(userToCreate)
        assertThat(result, `is`(persistedUser))
    }

    @Test
    fun shouldAddUserNameAndPictureFromAuth0() {
        val fbId = "someFbId"
        val userToCreate = User(fbId = fbId, givenName = "Bastian", familyName = "Stein", imageUrl = "someImgUrl")
        val persistedUser = User(id = 1, fbId = fbId, givenName = "Bastian", familyName = "Stein", imageUrl = "someImgUrl")

        val userInfo = mutableMapOf<String, Any>()
        userInfo.put("given_name", "Bastian")
        userInfo.put("family_name", "Stein")
        userInfo.put("picture", "someImgUrl")

        given(auth0ClientMock.getUserInfo("someAccessToken")).willReturn(userInfo)
        given(userRepositoryMock.findByFbId(fbId)).willReturn(null)
        given(userRepositoryMock.save(userToCreate)).willReturn(persistedUser)

        val result = testee.createOrUpdate(fbId, "someAccessToken")

        assertThat(result, `is`(persistedUser))
    }
}
