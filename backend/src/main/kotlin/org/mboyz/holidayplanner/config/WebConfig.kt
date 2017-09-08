package org.mboyz.holidayplanner.config

import org.mboyz.holidayplanner.holiday.HolidayArgumentResolver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

@Configuration
class WebConfig: WebMvcConfigurerAdapter() {
    @Autowired
    lateinit var holidayArgumentResolver: HolidayArgumentResolver

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>?) {
        super.addArgumentResolvers(argumentResolvers)
        argumentResolvers!!.add(holidayArgumentResolver)
    }

    @Bean
    fun templateResolver(): ClassLoaderTemplateResolver {
        val templateResolver = ClassLoaderTemplateResolver()
        templateResolver.prefix = "templates/"
        templateResolver.suffix = ".html"
        templateResolver.isCacheable = false
        templateResolver.templateMode = "HTML5"
        templateResolver.characterEncoding = "UTF-8"
        return templateResolver
    }
}