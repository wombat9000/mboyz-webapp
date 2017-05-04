package org.mboyz.holidayplanner

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.context.annotation.ComponentScan
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter
import java.util.*

@ComponentScan
@SpringBootApplication
open class Server : SpringBootServletInitializer() {

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val getenv: String? = System.getenv("PORT")
            var PORT = 8080
            if (getenv != null) {
                PORT = Integer.valueOf(getenv)
            }

            val props = HashMap<String, Any>()
            props.put("server.port", PORT)
            SpringApplicationBuilder()
                    .sources(Server::class.java)
                    .properties(props)
                    .run(*args)
        }
    }
}