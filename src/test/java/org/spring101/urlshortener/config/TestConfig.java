package org.spring101.urlshortener.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Properties;
import javax.sql.DataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 */
@Configuration
@ComponentScan(basePackages = "org.spring101.urlshortener")
@EnableJpaRepositories(basePackages = "org.spring101.urlshortener.repository")
@EnableTransactionManagement
public class TestConfig {
    
    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.apache.derby.jdbc.EmbeddedDriver");
        dataSource.setUrl("jdbc:derby:url-shortener;create=true;");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    }

    @Bean(name = "transactionManager")
    public JpaTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.afterPropertiesSet();
        return transactionManager;
    }

    @Bean(name = "entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setPackagesToScan(
                "org.spring101.urlshortener.entity"
        );
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactory.setJpaProperties(jpaProperties());
        entityManagerFactory.setDataSource(dataSource());
        entityManagerFactory.afterPropertiesSet();
        return entityManagerFactory;
    }

    /**
     * JPA properties for in-memory database
     *
     * @return Properties instance
     */
    private Properties jpaProperties() {
        return new Properties() {
            {
                put("hibernate.dialect", "org.hibernate.dialect.DerbyDialect");
                put("hibernate.hbm2ddl.auto", "create");
                put("hibernate.show_sql", "false");
                put("hibernate.temp.use_jdbc_metadata_defaults", "false");
                put("hibernate.cache.use_second_level_cache", "false");
                put("hibernate.cache.use_query_cache", "false");
                put("hibernate.enable_lazy_load_no_trans", "true");
                put("org.hibernate.envers.track_entities_changed_in_revision", "true");
                put("org.hibernate.envers.global_with_modified_flag", "true");
                put("org.hibernate.envers.store_data_at_delete", "true");
                put("org.hibernate.envers.audit_table_prefix", "audit_");
                put("org.hibernate.envers.audit_table_suffix", "");
                put("org.hibernate.envers.modified_flag_suffix", "_changed");
                put("org.hibernate.envers.revision_field_name", "revision");
                put("org.hibernate.envers.revision_type_field_name", "revision_type");
            }
        };
    }
    
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
