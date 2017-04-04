package org.mboyz.holidayplanner.user


import org.junit.Test
import org.mockito.Mockito.*

class UserControllerTest {

    @Test
    fun itShouldPersistANewUser() {
        val unpersistedUser = UnpersistedUser("someFirst", "someLast", "someMail", "somePW")
        val userServiceMock: UserService = mock(UserService::class.java)
        val testee = UserController(userServiceMock)

        testee.create("someFirst", "someLast", "someMail", "somePW")

        verify(userServiceMock).persist(unpersistedUser)
    }
}
