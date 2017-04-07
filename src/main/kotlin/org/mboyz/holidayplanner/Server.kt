package org.mboyz.holidayplanner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.EnableAutoConfiguration
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.web.support.SpringBootServletInitializer
import org.springframework.boot.builder.SpringApplicationBuilder
import java.util.HashMap





@ComponentScan
@EnableAutoConfiguration(exclude = arrayOf(DataSourceAutoConfiguration::class))
@SpringBootApplication
open class Server : SpringBootServletInitializer(){

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            val getenv: String? = System.getenv("PORT")

            var PORT = 8080

            if (getenv != null) {
                PORT = Integer.valueOf(getenv)
            }

            val props = HashMap<String, Any>()
            props.put("server.port", PORT)

//            SpringApplication.run(Server::class.java, *args)

            SpringApplicationBuilder()
                    .sources(Server::class.java)
                    .properties(props)
                    .run(*args)
        }
    }

}