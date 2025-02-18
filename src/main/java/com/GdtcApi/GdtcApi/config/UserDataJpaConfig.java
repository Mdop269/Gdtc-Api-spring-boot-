//package com.GdtcApi.GdtcApi.config;
//
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackages = "com.GdtcApi.GdtcApi.Repsoitory",
//        entityManagerFactoryRef = "userDataEntityManagerFactory",
//        transactionManagerRef = "userDataTransactionManager"
//)
//public class UserDataJpaConfig {
//
//    @ConfigurationProperties(prefix = "spring.datasource.user")
//    public DataSource mainDataSource() {
//        return DataSourceBuilder.create().build();
//    }
//
//    @Bean
//    public LocalContainerEntityManagerFactoryBean userDataEntityManagerFactory(
//            EntityManagerFactoryBuilder builder, DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.GdtcApi.GdtcApi.Entities")
//                .persistenceUnit("user")
//                .build();
//    }
//
//    @Bean
//    public PlatformTransactionManager userDataTransactionManager(
//            @Qualifier("userDataEntityManagerFactory") LocalContainerEntityManagerFactoryBean emf) {
//        return new JpaTransactionManager(emf.getObject());
//    }
//}
