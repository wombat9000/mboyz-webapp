package org.mboyz.holidayplanner

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication(scanBasePackages = arrayOf("org.mboyz.holidayplanner"))
open class Server {
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            SpringApplication.run(Server::class.java, *args)
        }
    }
}