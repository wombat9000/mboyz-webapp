package org.mboyz.holidayplanner.user


import org.junit.Test
import org.mockito.Mockito.*

class UserControllerTest {

    @Test
    fun itShouldCreateANewUser() {
        val userServiceMock: UserService = mock(UserService::class.java)
        val testee = UserController(userServiceMock)

        testee.create("Bastian", "Stein", "someEmail", "somePW")

        verify(userServiceMock).create("Bastian", "Stein", "someEmail", "somePW")
    }
}
