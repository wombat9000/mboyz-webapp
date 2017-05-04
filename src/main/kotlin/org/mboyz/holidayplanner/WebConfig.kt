package org.mboyz.holidayplanner

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter

@Configuration
class WebConfig: WebMvcConfigurerAdapter() {

    @Autowired
    lateinit var holidayArgumentResolver: HolidayArgumentResolver

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
        super.addArgumentResolvers(argumentResolvers)
        argumentResolvers!!.add(holidayArgumentResolver)
    }
}