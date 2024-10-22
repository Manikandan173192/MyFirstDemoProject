package com.maxbyte.sam.DatabaseConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Objects;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.maxbyte.sam.OracleDBFlow.Repository",
        //basePackages = @Value(value = "${main.db.driver}"),
        entityManagerFactoryRef = "OracleEntityManager",
        transactionManagerRef = "OracleTransactionManager"
)
public class OracleDatabase {
    @Autowired
    private Environment env;

    @Bean(name = "OracleEntityManager")
    public LocalContainerEntityManagerFactoryBean OracleEntityManager() {
        LocalContainerEntityManagerFactoryBean em =
                new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(SAMDataSource());
        em.setPackagesToScan(
                new String[]{"com.maxbyte.sam.OracleDBFlow.Entity"});
        em.setPersistenceUnitName("OracleTransactionManager");
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.properties.hibernate.dialect"));
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "OracleDataSource")
    @ConfigurationProperties(prefix = "oracle.db")
    public DataSource SAMDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("oracle.db.url"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("oracle.db.driver")));
        dataSource.setUsername(env.getProperty("oracle.db.username"));
        dataSource.setPassword(env.getProperty("oracle.db.password"));

        return dataSource;
    }

    @Bean(name = "OracleTransactionManager")
    public PlatformTransactionManager OracleTransactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(OracleEntityManager().getObject());
        return transactionManager;
    }


    @Bean
    public DataSourceProperties dataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    public DataSource dataSource(DataSourceProperties dataSourceProperties) {
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
