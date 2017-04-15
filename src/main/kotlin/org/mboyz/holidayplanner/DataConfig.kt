package org.mboyz.holidayplanner

import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI
import java.net.URISyntaxException
import javax.sql.DataSource


@Configuration
class DataConfig {

    @Bean
    @Throws(URISyntaxException::class)
    fun dataSource(): DataSource {
        val dbUri = URI(System.getenv("DATABASE_URL"))

        val username = dbUri.getUserInfo().split(":")[0]
        val password = dbUri.getUserInfo().split(":")[1]
        val dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath()

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