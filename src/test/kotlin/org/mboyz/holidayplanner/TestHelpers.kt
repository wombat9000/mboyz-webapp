package org.mboyz.holidayplanner

import org.mockito.Mockito

@Suppress("UNCHECKED_CAST")
fun <T> any(): T {
    Mockito.any<T>()
    return null as T
}