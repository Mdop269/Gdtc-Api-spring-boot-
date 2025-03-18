package com.GdtcApi.GdtcApi.Service.SecurityService;

import com.GdtcApi.GdtcApi.Repsoitory.UsersRepo;
import com.GdtcApi.GdtcApi.RequestDTO.UsersRequest;
import com.GdtcApi.GdtcApi.ResponseDTO.UsersResponse;
import com.GdtcApi.GdtcApi.Entities.Users;
import jakarta.persistence.EntityManagerFactory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

@Service
public class UsersService {

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JWTService jwtService;

    @Autowired
    @Qualifier("superUserJdbcTemplate")
    private JdbcTemplate superUserJdbcTemplate;

    // values from properties for tenant DB creation
    @Value("${spring.datasource.base-url}")
    private String baseUrl; // e.g. "jdbc:postgresql://localhost:5432/"

    @Value("${spring.datasource.tenant-username}")
    private String tenantUsername; // e.g. "postgres"

    @Value("${spring.datasource.tenant-password}")
    private String tenantPassword; // e.g. "postgres"



    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);



//    @Transactional
//    public Users registerUser(Users users)
//    {
//        return  usersRepo.save(users);
//    }

//    @Async
//    @Transactional // this is used for rollback means if we got error and before it changed something it will roll back
//    public CompletableFuture<UsersResponse> upsertUsersAsync(UsersRequest dto) {
//        // Convert DTO to entity
//        Users usersEntity = UsersRequest.MapToEntity(dto);
//
//        // Start async processing chain
//        return usersRepo.findByUsers(usersEntity.getUserName())
//                .thenComposeAsync(existingUsers -> {
//                    if (existingUsers != null) {
//                        // Update existing entity
//                        existingUsers.setPassword(usersEntity.getPassword());
//
//                        // Save the entity and map to DTO
//                        return CompletableFuture.supplyAsync(() -> {
//                            Users updated = usersRepo.save(existingUsers);
//                            return UsersResponse.MapToDto(updated);
//                        });
//                    } else {
//                        // Create new entity
//                        return CompletableFuture.supplyAsync(() -> {
//                            Users saved = usersRepo.save(usersEntity);
//                            createUserDatabase(saved.getUserName());
//                            return UsersResponse.MapToDto(saved);
//                        });
//                    }
//                })
//                .exceptionally(ex -> {
//                    throw new CompletionException("Failed to upsert Users", ex.getCause());
//                });
//    }


    //Asynchronous upsert operation : update if exists; create otherwise
//    @Async
//    @Transactional
//    public CompletableFuture<UsersResponse> upsertUsersAsync(UsersRequest dto) {
//        return CompletableFuture.supplyAsync(() -> {
//            // 1. Convert DTO to entity
//            Users userEntity = UsersRequest.MapToEntity(dto);
//
//            // 2. See if user already in master DB
//            Users existing = usersRepo.findByUserName(userEntity.getUserName());
//            try {
//                if (existing != null) {
//                    // update password for existing user
//                    existing.setPassword(userEntity.getPassword());
//                    Users updated = usersRepo.save(existing);
//                    // no need to create DB if it's already there
//                    return UsersResponse.MapToDto(updated);
//                } else {
//                    // create new user in master Db
//                    Users saved = usersRepo.save(userEntity);
//                    // create a new dedicated database for this user
//                    createUserDatabase(saved.getUserName());
//                    return UsersResponse.MapToDto(saved);
//                }
//            } catch (Exception ex) {
//                throw new CompletionException("Failed to upsert Users", ex);
//            }
//        });
//    }

    @Transactional
    public UsersResponse upsertUsers(UsersRequest dto) {

            // 1. Convert DTO to entity
            Users userEntity = UsersRequest.MapToEntity(dto);

            // 2. See if user already in master DB
            Users existing = usersRepo.findByUserName(userEntity.getUserName());
            try {
                if (existing != null) {
                    // update password for existing user
                    existing.setPassword(userEntity.getPassword());
                    Users updated = usersRepo.save(existing);
                    // no need to create DB if it's already there
                    return UsersResponse.MapToDto(updated);
                } else {
                    // create new user in master Db
                    Users saved = usersRepo.save(userEntity);
                    // create a new dedicated database for this user
                    createUserDatabase(saved.getUserName());
                    return UsersResponse.MapToDto(saved);
                }
            } catch (Exception ex) {
                throw new CompletionException("Failed to upsert Users", ex);
            }
    }


    //    @Transactional
//    public UsersResponse registerUser(UsersRequest dto){
//        // Convert DTO to entity
//        Users usersEntity = UsersRequest.MapToEntity(dto);
//
//       Users existingUsers =  usersRepo.findByUsersSync(usersEntity.getUserName());
//
//       if(existingUsers != null)
//       {
//           existingUsers.setPassword(usersEntity.getPassword());
//           Users updated = usersRepo.save(existingUsers);
//           return UsersResponse.MapToDto(updated);
//       }
//       else{
//
//           Users saved = usersRepo.save(usersEntity);
//           createUserDatabase(saved.getUserName());
//           return UsersResponse.MapToDto(saved);
//       }
//    }

    // verifies user credentials and retuyrns a jwt token if successfull
    public String verify(UsersRequest usersRequest) {
        // Authenticate the user using spring security
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usersRequest.getUserName(), usersRequest.getPassword()));

        if(authentication.isAuthenticated())
            return jwtService.generateToken(usersRequest.getUserName());

        return "Failed";
    }

//    private void createUserDatabase(String username) {
//        String sanitizedDbName = sanitizeDatabaseName(username);
//        try {
//            superUserJdbcTemplate.execute("CREATE DATABASE " + sanitizedDbName);
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to create user database", e);
//        }
//    }
//

    //Helper: santize the username to form a valid database name
    private String sanitizeDatabaseName(String username) {
        return username.replaceAll("[^a-zA-Z0-9_]", "_").toLowerCase();
    }

    //create a new tenant database for the user
    private void createUserDatabase(String username) {
        String sanitizedDbName = sanitizeDatabaseName(username);
        try {
            //create database using superuser connection
            superUserJdbcTemplate.execute("CREATE DATABASE " + sanitizedDbName);
            //Initialize tables/entites using Hibernate auti-DDL
            initializeTenantSchemaViaHibernate(sanitizedDbName);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create user database", e);
        }
    }

//    private void initializeTenantSchema(String dbName) throws SQLException {
//        // The superUserJdbcTemplate is pointed to 'postgres', so we need to build a new DataSource
//        // or just do the string replacement ourselves:
//        String url = superUserJdbcTemplate.getDataSource()
//                .getConnection()
//                .getMetaData()
//                .getURL(); // e.g. "jdbc:postgresql://localhost:5432/postgres"
//
//        // Replace the final segment with new DB name
//        String tenantUrl = url.replaceFirst("/[^/]+$", "/" + dbName);
//
//        // Use super user for migrations
//        Flyway flyway = Flyway.configure()
//                .dataSource(tenantUrl, "postgres", "postgres")
//                .load();
//        flyway.migrate();
//    }

    /**
     * Builds a temporary EntityManagerFactory for the new DB and
     * uses hibernate.hbm2ddl.auto=update to create all your Entities/tables.
     */
    // Creare ab EntityManagerFactory for the new DB to generate schema
    private void initializeTenantSchemaViaHibernate(String dbName) {
        // 1. Build a DataSource that points to the brand-new DB
        String jdbcUrl = baseUrl + dbName; // e.g. "jdbc:postgresql://localhost:5432/username_db"
        DataSource tenantDS = DataSourceBuilder.create()
                .url(jdbcUrl)
                .username(tenantUsername)
                .password(tenantPassword)
                .driverClassName("org.postgresql.Driver")
                .build();

        // 2. Create a Builds a temporary EntityManagerFactory for the new DB and
        //      uses hibernate.hbm2ddl.auto=update to create all your Entities/tables.
        LocalContainerEntityManagerFactoryBean emfBean = new LocalContainerEntityManagerFactoryBean();
        emfBean.setDataSource(tenantDS);
        emfBean.setPackagesToScan("com.GdtcApi.GdtcApi.Entities");
        emfBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        // 3. Set properties so Hibernate will auto-generate your schema
        Map<String, Object> jpaProps = new HashMap<>();
        jpaProps.put("hibernate.hbm2ddl.auto", "update");
        jpaProps.put("hibernate.show_sql", "false");
        // If you want more config, add it here.
        emfBean.setJpaPropertyMap(jpaProps);

        // 4. Initialize the EMF (this triggers schema creation)
        emfBean.afterPropertiesSet();

        // 5. Obtain the actual EMF and then close it
        EntityManagerFactory emf = emfBean.getObject();
        if (emf != null) {
            emf.close();
        }
    }






}
