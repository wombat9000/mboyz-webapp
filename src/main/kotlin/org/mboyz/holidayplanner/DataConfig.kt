package org.mboyz.holidayplanner

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.net.URI
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
class DataConfig {

    @Bean
    @Profile("prod")
    @Throws(URISyntaxException::class)
    fun dataSource(): DataSource {
        val getenv: String? = System.getenv("DATABASE_URL")
        val dbUri: URI? = URI(getenv)

        val username: String = dbUri?.userInfo?.split(":")?.get(0)?: "mboyz_app"
        val password = dbUri?.userInfo?.split(":")?.get(1)?: "postgres"

        val port = if (dbUri?.port!! > 0) dbUri.port else 5432
        val path: String? = dbUri.path ?: "/mboyz"
        val host: String? = dbUri.host ?: "localhost"
        val dbUrl = "jdbc:postgresql://$host:$port$path"

        val basicDataSource = DataSourceBuilder
                .create()
                .username(username)
                .password(password)
                .url(dbUrl)
                .driverClassName("org.postgresql.Driver")
                .build()

        return basicDataSource
    }
}