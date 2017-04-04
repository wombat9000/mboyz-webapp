package org.mboyz.holidayplanner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.boot.web.support.SpringBootServletInitializer

@ComponentScan
@SpringBootApplication
open class Server : SpringBootServletInitializer(){

    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Server::class.java, *args)
        }
    }

}