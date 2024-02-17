package ru.astra.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
public class DbConfig extends HikariConfig {
    private final Environment env;

    public DbConfig(Environment env) {
        this.env = env;
    }

    @Bean
    public DataSource dataSource() {
        var hikariConfig = new HikariConfig();

        hikariConfig.setDriverClassName("org.postgresql.Driver");
        hikariConfig.setJdbcUrl("jdbc:postgresql://localhost:5432/inno");
        hikariConfig.setUsername("postgres");
        hikariConfig.setPassword("postgres");
        hikariConfig.setSchema("users");
        hikariConfig.setMaximumPoolSize(5);
        hikariConfig.setMinimumIdle(2);

        return new HikariDataSource(hikariConfig);
    }
}