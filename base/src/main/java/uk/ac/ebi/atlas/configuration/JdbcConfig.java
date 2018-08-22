package uk.ac.ebi.atlas.configuration;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Properties;

@Configuration
@PropertySource("classpath:jdbc.properties")
@EnableTransactionManagement
public class JdbcConfig {
    private final HikariConfig hikariConfig;

    public JdbcConfig(@Value("${jdbc.pool}") String poolName,
                      @Value("${jdbc.url}") String jdbcUrl,
                      @Value("${jdbc.username}") String jdbcUserName,
                      @Value("${jdbc.password}") String jdbcPassword) {
        hikariConfig = new HikariConfig();
        hikariConfig.setDataSourceClassName("org.postgresql.ds.PGSimpleDataSource");
        hikariConfig.setPoolName(poolName + "Hikari");

        Properties dataSourceProperties = new Properties();
        dataSourceProperties.setProperty("url", jdbcUrl);
        dataSourceProperties.setProperty("user", jdbcUserName);
        dataSourceProperties.setProperty("password", jdbcPassword);

        hikariConfig.setDataSourceProperties(dataSourceProperties);

        hikariConfig.setConnectionTestQuery("SELECT 1");
    }

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource() {
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public NamedParameterJdbcTemplate namedParameterJdbcTemplate() {
        return new NamedParameterJdbcTemplate(dataSource());
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }
}
