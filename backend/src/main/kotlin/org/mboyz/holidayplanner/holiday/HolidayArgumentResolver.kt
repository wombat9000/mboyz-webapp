package org.mboyz.holidayplanner.holiday

import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import java.time.LocalDate
import javax.servlet.http.HttpServletRequest

@Configuration
open class HolidayArgumentResolver : HandlerMethodArgumentResolver {
    override fun resolveArgument(parameter: MethodParameter?, mavContainer: ModelAndViewContainer?, webRequest: NativeWebRequest?, binderFactory: WebDataBinderFactory?): Any {
        val servletRequest: HttpServletRequest = webRequest!!.getNativeRequest(HttpServletRequest::class.java)

        val name: String = servletRequest.getParameter("name")
        val location: String = servletRequest.getParameter("location") ?: ""

        val startDate = servletRequest.getParameter("startDate").toLocalDate()
        val endDate = servletRequest.getParameter("endDate").toLocalDate()

        return Holiday(
                name = name,
                location = location,
                startDate = startDate,
                endDate = endDate
        )
    }

    override fun supportsParameter(parameter: MethodParameter?): Boolean {
        return parameter!!.parameterType == Holiday::class.java
    }
}

private fun String.toLocalDate(): LocalDate? {
    return if (this.isEmpty()) null else LocalDate.parse(this)
}