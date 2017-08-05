package org.mboyz.holidayplanner

import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.mboyz.holidayplanner.holiday.Holiday
import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
fun <T> any(): T {
    Mockito.any<T>()
    return null as T
}

infix fun Holiday.isIncludedIn(row: List<String>): Unit {
    val expectedRow = "${this.name} ${this.location} ${this.startDate} ${this.endDate}"
    MatcherAssert.assertThat("row contains $expectedRow", row.contains(expectedRow), CoreMatchers.`is`(true))
}

