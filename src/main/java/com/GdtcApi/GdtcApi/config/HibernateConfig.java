package com.GdtcApi.GdtcApi.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//public class HibernateConfig {
//
//    @Autowired
//    private DataSource dataSource;
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
//        em.setDataSource(dataSource);
//        em.setPackagesToScan("com.GdtcApi.GdtcApi.Entities");
//        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
//
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("hibernate.multiTenancy", "DATABASE");
//        properties.put("hibernate.multi_tenant_connection_provider",
//                new MultiTenantConnectionProviderImpl(dataSource));
//        properties.put("hibernate.tenant_identifier_resolver",
//                new CurrentTenantIdentifierResolverImpl());
//
//        em.setJpaPropertyMap(properties);
//        return em;
//    }
//}
//


@Configuration
public class HibernateConfig {

    @Autowired
    private DataSource dataSource; // This is the master DataSource

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(
            MultiTenantConnectionProviderImpl multiTenantConnectionProvider,
            CurrentTenantIdentifierResolverImpl tenantIdentifierResolver
    ) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource); // For “master” queries
        em.setPackagesToScan("com.GdtcApi.GdtcApi.Entities");

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> props = new HashMap<>();
        props.put("hibernate.multiTenancy", "DATABASE");
        props.put("hibernate.multi_tenant_connection_provider", multiTenantConnectionProvider);
        props.put("hibernate.tenant_identifier_resolver", tenantIdentifierResolver);

        em.setJpaPropertyMap(props);
        return em;
    }
}
