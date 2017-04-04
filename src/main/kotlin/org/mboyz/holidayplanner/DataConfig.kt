package org.mboyz.holidayplanner

import org.mybatis.spring.SqlSessionFactoryBean
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DataSourceTransactionManager
import org.springframework.jdbc.datasource.SimpleDriverDataSource
import javax.sql.DataSource

@Configuration
@MapperScan("org.mboyz.holidayplanner")
class DataConfig {

    @Bean
    fun dataSource(): DataSource {
        val dataSource = SimpleDriverDataSource()
        dataSource.setDriverClass(org.postgresql.Driver::class.java)

        //TODO: read config from application properties
        dataSource.username = "mboyz_app"
        dataSource.url = "jdbc:postgresql://localhost:5432/mboyz"
        dataSource.password = "postgres"

        return dataSource
    }

    @Bean
    fun transactionManager(): DataSourceTransactionManager {
        return DataSourceTransactionManager(dataSource())
    }

    @Bean
    @Throws(Exception::class)
    fun sqlSessionFactory(): SqlSessionFactoryBean {
        val sessionFactory = SqlSessionFactoryBean()
        sessionFactory.setDataSource(dataSource())
        sessionFactory.setTypeAliasesPackage("org.mboyz.holidayplanner")
        return sessionFactory
    }
}