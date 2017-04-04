package org.mboyz.holidayplanner.user

import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class UserServiceTest {

    @Test
    fun itShouldPersistAnUnpersistedUser() {
        val userMapperMock = mock(UserMapper::class.java)
        val testee = UserService(userMapperMock)

        val unpersistedUser = UnpersistedUser("someFirst", "someLast", "someMail", "somePW")

        testee.persist(unpersistedUser)

        verify(userMapperMock).insert(unpersistedUser)
    }
}