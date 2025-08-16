package com.codqueto.facebook_copy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;


/**
 * Lo que yo tengo en el application.properties al ser springboot starter, es como si estuviera haciendo esto, pero por
 * medio de DI.
 */
//@Configuration
public class DataSourceConfig
{

//    @Bean
    public void DataSource() {
       final var dataSource = new DriverManagerDataSource();
       dataSource.setDriverClassName("");
       dataSource.setUrl("");
       dataSource.setUsername("");
       dataSource.setPassword("");
    }
}
