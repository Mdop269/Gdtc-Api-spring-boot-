package com.GdtcApi.GdtcApi.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
//public class DataSourceConfig {
//
//    @Value("${spring.datasource.url}")
//    private String baseUrl;
//
//    @Value("${spring.datasource.username}")
//    private String username;
//
//    @Value("${spring.datasource.password}")
//    private String password;
//
//    @Value("${spring.super.datasource.url}")
//    private String superUserUrl;
//
//    @Value("${spring.super.datasource.username}")
//    private String superUserUsername;
//
//    @Value("${spring.super.datasource.password}")
//    private String superUserPassword;
//
//    @Bean
//    @Primary
//    public DataSource dataSource() {
//        TenantAwareRoutingDataSource routingDataSource = new TenantAwareRoutingDataSource();
//        DataSource defaultDataSource = DataSourceBuilder.create()
//                .url(baseUrl)
//                .username(username)
//                .password(password)
//                .build();
//
//        Map<Object, Object> targetDataSources = new HashMap<>();
//        targetDataSources.put("mdop", defaultDataSource);
//
//        routingDataSource.setTargetDataSources(targetDataSources);
//        routingDataSource.setDefaultTargetDataSource(defaultDataSource);
//
//        return routingDataSource;
//    }
//
////    @Bean(name = "superUserDataSource")
////    public DataSource tenantDataSource(@Value("${spring.datasource.url}") String url) {
////        String tenantId = TenantContext.getCurrentTenant();
////        if (tenantId == null) {
////            tenantId = "mdop";
////        }
////        String tenantUrl = url.replace("/mdop", "/" + tenantId);
////        System.out.println("Connecting to database: " + tenantUrl);
////        return DataSourceBuilder.create()
////                .url(tenantUrl)
////                .username(username)
////                .password(password)
////                .build();
////    }
//
//
//      @Bean(name = "superUserDataSource")
//    public DataSource superUserDataSource() {
//          return DataSourceBuilder.create()
//                .url(superUserUrl)
//                .username(superUserUsername)
//                .password(superUserPassword)
//                .build();
//    }
//
//    @Bean(name = "superUserJdbcTemplate")
//    public JdbcTemplate superUserJdbcTemplate(@Qualifier("superUserDataSource") DataSource dataSource) {
//        return new JdbcTemplate(dataSource);
//    }
//}


@Configuration
public class DataSourceConfig {

    // Master datasource for user authentication
    @Value("${spring.datasource.url}")
    private String masterUrl; // e.g. jdbc:postgresql://localhost:5432/mdop
    @Value("${spring.datasource.username}")
    private String masterUsername;
    @Value("${spring.datasource.password}")
    private String masterPassword;

    //Super user datasource for creating tenant database
    @Value("${spring.super.datasource.url}")
    private String superUserUrl; // e.g. jdbc:postgresql://localhost:5432/postgres
    @Value("${spring.super.datasource.username}")
    private String superUserUsername;
    @Value("${spring.super.datasource.password}")
    private String superUserPassword;

    @Bean
    @Primary
    public DataSource dataSource() {
        // Master data source: points to mdop, used for storing user records
        return DataSourceBuilder.create()
                .url(masterUrl)
                .username(masterUsername)
                .password(masterPassword)
                .build();
    }

    @Bean(name = "superUserDataSource")
    public DataSource superUserDataSource() {
        return DataSourceBuilder.create()
                .url(superUserUrl)
                .username(superUserUsername)
                .password(superUserPassword)
                .build();
    }

    @Bean(name = "superUserJdbcTemplate")
    public JdbcTemplate superUserJdbcTemplate(
            @Qualifier("superUserDataSource") DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }
}
