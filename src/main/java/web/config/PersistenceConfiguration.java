package web.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;
import java.util.Properties;

@Configuration
@PropertySource("/WEB-INF/classes/db.properties")
@ComponentScan(basePackages = "web")
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "web")
public class PersistenceConfiguration {

    @Autowired
    private Environment env;

    @Bean
    public BasicDataSource dataSource() {

        final BasicDataSource dataSource = new BasicDataSource();

        System.out.println(env.getProperty("classpath"));

        dataSource.setDriverClassName(env.getProperty("db.driver.class"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));

        dataSource.setInitialSize(Integer.parseInt(Optional.ofNullable(env.getProperty("dbcp.initial.size")).orElse("0")));
        dataSource.setMaxIdle(Integer.parseInt(Optional.ofNullable(env.getProperty("dbcp.max.idle")).orElse("5")));
        dataSource.setMaxTotal(Integer.parseInt(Optional.ofNullable(env.getProperty("dbcp.max.total")).orElse("0")));
        dataSource.setMinIdle(Integer.parseInt(Optional.ofNullable(env.getProperty("dbcp.min.idle")).orElse("0")));

        return dataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {

        final LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan("web");

        final HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        entityManagerFactoryBean.setJpaVendorAdapter(vendorAdapter);
        entityManagerFactoryBean.setJpaProperties(additionalProperties());

        return entityManagerFactoryBean;
    }

    final Properties additionalProperties() {

        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", env.getProperty("hibernate.dialect"));

        hibernateProperties.setProperty("hibernate.cache.use_second_level_cache", env.getProperty("hibernate.cache.use_second_level_cache"));
        hibernateProperties.setProperty("hibernate.cache.use_query_cache", env.getProperty("hibernate.cache.use_query_cache"));

        return hibernateProperties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory emf) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(emf);
        return transactionManager;
    }

}
