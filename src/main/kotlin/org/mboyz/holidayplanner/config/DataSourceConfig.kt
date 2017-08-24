package org.mboyz.holidayplanner.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
class DataSourceConfig {

    @Value("\${database.url}")
    lateinit var dbURL: String

    @Bean
    @Throws(URISyntaxException::class)
    fun dataSource(): DataSource {
        val dbUri: URI = URI(dbURL)

        val username: String = dbUri.userInfo?.split(":")?.get(0)?: "mboyz_app"
        val password = dbUri.userInfo?.split(":")?.get(1)?: "postgres"

        val port = if (dbUri.port > 0) dbUri.port else 5432
        val path: String? = dbUri.path ?: "/mboyz"
        val host: String? = dbUri.host ?: "localhost"
        val dbUrl = "jdbc:postgresql://$host:$port$path"

        return DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(dbUrl)
                .driverClassName("org.postgresql.Driver")
                .build()
    }
}