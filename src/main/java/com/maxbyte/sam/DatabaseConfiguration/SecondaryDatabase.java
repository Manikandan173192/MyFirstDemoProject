package com.maxbyte.sam.DatabaseConfiguration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
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
        basePackages = {"com.maxbyte.sam.SecondaryDBFlow.Authentication.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.Configuration.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.AIM.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.CWF.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.RCA.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.CAPA.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.IB.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.FMEA.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.SA.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.MOC.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.Scheduler.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.WRWOType.Repository",
                "com.maxbyte.sam.SecondaryDBFlow.FNDUser.Repository",

        },
        entityManagerFactoryRef = "SecondaryEntityManager",
        transactionManagerRef = "SecondaryTransactionManager"
)
public class SecondaryDatabase {
    @Autowired
    private Environment env;

    @Bean(name = "SecondaryEntityManager")
    public LocalContainerEntityManagerFactoryBean SecondaryEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(MaxDataSource());
        em.setPackagesToScan(
                new String[]{"com.maxbyte.sam.SecondaryDBFlow.Authentication.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.Configuration.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.AIM.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.CWF.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.RCA.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.CAPA.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.IB.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.FMEA.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.SA.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.MOC.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.Scheduler.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.WorkOrder.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.WorkRequest.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.WRWOType.Entity",
                        "com.maxbyte.sam.SecondaryDBFlow.FNDUser.Entity",

                });
        em.setPersistenceUnitName("SecondaryTransactionManager");
        HibernateJpaVendorAdapter vendorAdapter =
                new HibernateJpaVendorAdapter();
        vendorAdapter.setGenerateDdl(true);
        vendorAdapter.setDatabasePlatform(env.getProperty("spring.jpa.hibernate.dialect"));
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean(name = "SecondaryDataSource")
    @ConfigurationProperties(prefix = "secondary.db")
    public DataSource MaxDataSource() {
        DriverManagerDataSource dataSource =
                new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("secondary.db.url"));
        dataSource.setDriverClassName(Objects.requireNonNull(env.getProperty("secondary.db.driver")));
        dataSource.setUsername(env.getProperty("secondary.db.username"));
        dataSource.setPassword(env.getProperty("secondary.db.password"));
        return dataSource;

    }

    @Bean(name = "SecondaryTransactionManager")
    public PlatformTransactionManager SecondaryTransactionManager(){
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(
                SecondaryEntityManager().getObject());
        return transactionManager;
    }

}
