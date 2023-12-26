package org.dk.multipleDbDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(
        basePackages = {"org.dk.multipleDbDemo.authordb"},
        entityManagerFactoryRef = "authorEntityManager",
        transactionManagerRef = "authorTxnManager"
)
public class AuthorDbConfig {

    @Autowired
    Environment environment;
    @Bean  // basically we want a bean which creates datasource for the person
    @ConfigurationProperties(prefix = "spring.authords")
    public DataSource authorDataSource(){
        return DataSourceBuilder.create().build();

    }

    @Bean
    public LocalContainerEntityManagerFactoryBean authorEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(authorDataSource());
        em.setPackagesToScan("org.dk.multipleDbDemo.authordb");

        //hibernate properties
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto",environment.getProperty("spring.authords.ddl-auto"));
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager authorTxnManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(authorEntityManager().getObject()); //.getObject() resolved error
        return jpaTransactionManager;
    }
}

// earlier we had only one datasource
// we were able to map the default entity manager
// entity manager is an interface of jpa and implementation by hibernate
