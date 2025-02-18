//package com.GdtcApi.GdtcApi.config;
//
//import com.GdtcApi.GdtcApi.Repsoitory.UsersRepo;
//import jakarta.persistence.EntityManagerFactory;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.boot.autoconfigure.domain.EntityScan;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
//import org.springframework.orm.jpa.JpaTransactionManager;
//import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
//import org.springframework.transaction.PlatformTransactionManager;
//
//import javax.sql.DataSource;
//
//@Configuration
//@EnableJpaRepositories(
//        basePackageClasses = UsersRepo.class,
//        entityManagerFactoryRef = "mainEntityManagerFactory",
//        transactionManagerRef = "mainTransactionManager"
//)
//@EntityScan(basePackages = "com.GdtcApi.GdtcApi.SecurityModel")
//public class MainJpaConfig {
////    @ConfigurationProperties(prefix = "spring.datasource.main")
////    public DataSource mainDataSource() {
////        return DataSourceBuilder.create().build();
////    }
//
//    @Primary
//    @Bean(name = "mainEntityManagerFactory")
//    public LocalContainerEntityManagerFactoryBean mainEntityManagerFactory(
//            EntityManagerFactoryBuilder builder,
//            @Qualifier("mainDataSource") DataSource dataSource) {
//        return builder
//                .dataSource(dataSource)
//                .packages("com.GdtcApi.GdtcApi.SecurityModel") // Ensure this matches your entity package
//                .persistenceUnit("main")
//                .build();
//    }
//
//    @Bean(name = "mainTransactionManager")
//    public PlatformTransactionManager mainTransactionManager(
//            @Qualifier("mainEntityManagerFactory") EntityManagerFactory entityManagerFactory) {
//        return new JpaTransactionManager(entityManagerFactory);
//    }
//}
