package org.mboyz.holidayplanner

import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles(profiles = arrayOf("local"))
@AutoConfigureMockMvc
class PageControllerTest {
    @Autowired
    var mvc: MockMvc? = null

    @Test
    fun shouldShowHomePage() {
        mvc!!.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("home"))
    }

    // TODO: setup inMemoryDB config for tests
}