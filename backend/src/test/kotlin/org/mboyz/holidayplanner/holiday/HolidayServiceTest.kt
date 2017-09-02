package org.mboyz.holidayplanner.holiday

import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Before
import org.junit.Test
import org.mboyz.holidayplanner.user.persistence.UserRepository
import org.mockito.BDDMockito.*
import org.mockito.Mock
import org.mockito.MockitoAnnotations.initMocks
import java.time.LocalDate

class HolidayServiceTest {

    lateinit var testee: HolidayService

    @Mock
    lateinit var userRepository: UserRepository

    @Mock
    lateinit var holidayRepository: HolidayRepository

    @Before
    fun setUp() {
        initMocks(this)
        testee = HolidayService(holidayRepository, userRepository)
    }

    @Test
    fun shouldFindAll() {
        val someHoliday = Holiday()
        given(holidayRepository.findAll()).willReturn(listOf(someHoliday))

        val result = testee.findAll()

        assertThat(result.any { it == someHoliday }, `is`(true))
    }

    @Test
    fun shouldFindSingle() {
        val someHoliday = Holiday()
        val someId: Long = 1
        given(holidayRepository.findOne(someId)).willReturn(someHoliday)

        val result = testee.findOne(someId)

        assertThat(result, `is`(someHoliday))
    }

    @Test(expected = HolidayNotFoundException::class)
    fun shouldThrowErrorIfHolidayNotFound() {
        given(holidayRepository.findOne(any())).willReturn(null)

        try {
            testee.findOne(1L)
        } catch (e: Exception) {
            throw(e)
        }
    }

    @Test
    fun shouldSaveHoliday() {
        val someHoliday = Holiday(1, "someName", "someLocation")
        given(holidayRepository.save(someHoliday)).willReturn(someHoliday)

        val result = testee.save(someHoliday)

        assertThat(result, `is`(someHoliday))
    }

    @Test(expected = InvalidDateRangeException::class)
    fun shouldThrowExceptionIfHolidayHasInvalidDates() {
        val holidayWithInvalidDates = Holiday(
                name = "someName",
                location = "someLocation",
                startDate = LocalDate.parse("1990-12-02"),
                endDate = LocalDate.parse("1990-12-01"))

        try {
            testee.save(holidayWithInvalidDates)
        } catch (e: Exception) {
            verifyZeroInteractions(holidayRepository)
            throw(e)
        }
    }

//    @Test
//    fun shouldSignUpUserForHoliday() {
//        val someUserId = 1337L
//        val someUser: UserEntity = UserEntity(someUserId)
//        val someHolidayId = 1L
//        val someHoliday: Holiday = Holiday(someHolidayId)
//
//        given(holidayService.findOne(someHolidayId)).willReturn(someHoliday)
//
//        testee.signUserUpForHoliday(someHolidayId, someUser)
//
//        val holidayWithParticipant = someHoliday.copy(users = listOf(someUserId))
//
//        verify(holidayService).add(holidayWithParticipant)
//    }

//    @Test
//    fun shouldSignUpUserForHolidayIfAnotherUserAlreadyOnList() {
//        val signUpUserId = 1337L
//        val someUser: UserEntity = UserEntity(signUpUserId)
//        val someHolidayId = 1L
//        val alreadySignedUpId = 2L
//        val someHolidayWithOtherParticipant: Holiday = Holiday(
//                id = someHolidayId,
//                users = listOf(alreadySignedUpId))
//
//        given(holidayService.findOne(someHolidayId)).willReturn(someHolidayWithOtherParticipant)
//
//        testee.signUserUpForHoliday(someHolidayId, someUser)
//
//        val holidayWithMultipleSignups = someHolidayWithOtherParticipant.copy(users = listOf(alreadySignedUpId, signUpUserId))
//
//        verify(holidayService).add(holidayWithMultipleSignups)
//    }
//
//    @Test
//    fun shouldNotSignUpUserForHolidayIfAlreadyOnList() {
//        val signUpUserId = 1337L
//        val someUser: UserEntity = UserEntity(signUpUserId)
//        val someHolidayId = 1L
//        val alreadySignedUpId = 1337L
//        val someHolidayWithOtherParticipant: Holiday = Holiday(
//                id = someHolidayId,
//                users = listOf(alreadySignedUpId))
//
//        given(holidayService.findOne(someHolidayId)).willReturn(someHolidayWithOtherParticipant)
//
//        testee.signUserUpForHoliday(someHolidayId, someUser)
//        verify(holidayService).findOne(someHolidayId)
//        verifyNoMoreInteractions(holidayService)
//    }
}
