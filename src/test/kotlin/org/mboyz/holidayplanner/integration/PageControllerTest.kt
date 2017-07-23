package org.mboyz.holidayplanner.integration

import org.junit.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.view

class PageControllerTest: AbstractSpringTest() {
    @Autowired
    lateinit var mvc: MockMvc

    @Test
    fun shouldShowHomePage() {
        mvc.perform(MockMvcRequestBuilders.get("/start"))
                .andExpect(status().isOk)
                .andExpect(view().name("home"))
    }

    @Test
    fun shouldShowReactHomePage() {
        mvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(status().isOk)
                .andExpect(view().name("homeReact"))
    }
}