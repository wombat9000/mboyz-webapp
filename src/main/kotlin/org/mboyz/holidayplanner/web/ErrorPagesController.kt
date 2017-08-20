package org.mboyz.holidayplanner.web

import org.eclipse.jetty.http.HttpStatus.FORBIDDEN_403
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.web.ErrorAttributes
import org.springframework.boot.autoconfigure.web.ErrorController
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.context.request.ServletRequestAttributes
import org.springframework.web.servlet.ModelAndView
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Controller
class ErrorPagesController(@Value("\${debug}") val debug: Boolean,
                           @Autowired val errorAttributes: ErrorAttributes) : ErrorController {

    @RequestMapping("/error")
    fun error(request: HttpServletRequest, response: HttpServletResponse): ModelAndView {
        if (response.status == FORBIDDEN_403) {
            return ModelAndView("error/forbidden")
        }

        val errorMap = getErrorAttributes(request, debug)
        return ModelAndView("error/generic", "errors", errorMap)
    }

    override fun getErrorPath(): String {
        return "/error"
    }

    private fun getErrorAttributes(request: HttpServletRequest, includeStackTrace: Boolean): Map<String, Any> {
        val requestAttributes = ServletRequestAttributes(request)
        return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace)
    }
}