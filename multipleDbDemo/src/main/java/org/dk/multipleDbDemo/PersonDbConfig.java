package org.dk.multipleDbDemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.origin.SystemEnvironmentOrigin;
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
        basePackages = {"org.dk.multipleDbDemo.persondb"},
        entityManagerFactoryRef = "personEntityManager", // I am telling here for this bean my entity manager is this,
        // if I dont define this here, then this bean will not be able to check the entityManagerFactoryRef
        transactionManagerRef = "personTxnManager" // if I not define this then transactionmanagerref will not be accessible, this is an important part
)
public class PersonDbConfig {


    @Autowired
    Environment environment; // with this we get all the properties which are defined in the application properties
    @Bean  // basically we want a bean which creates datasource for the person
    @ConfigurationProperties(prefix = "spring.personds")
    public DataSource personDataSource(){
        return DataSourceBuilder.create().build();

    }

    // we can provide hibernate properties to custom dbs
    @Bean
    public LocalContainerEntityManagerFactoryBean personEntityManager(){
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(personDataSource());
        em.setPackagesToScan("org.dk.multipleDbDemo.persondb");
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        Map<String,Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", environment.getProperty("spring.personds.ddl-auto")); //spring.jpa.hibernate.ddl-auto wont work for multiple datasources
        em.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        em.setJpaPropertyMap(properties);
        return em;
    }

    @Bean
    public PlatformTransactionManager personTxnManager(){
        JpaTransactionManager jpaTransactionManager = new JpaTransactionManager();
        jpaTransactionManager.setEntityManagerFactory(personEntityManager().getObject()); //.getObject() resolved error
        return jpaTransactionManager;
    }
}
