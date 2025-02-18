package com.GdtcApi.GdtcApi.Repsoitory;

import com.GdtcApi.GdtcApi.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.concurrent.CompletableFuture;

@Repository
public interface UsersRepo extends JpaRepository<Users, Integer> {

//    Users findByUsername(String userName);

    Users findByUserName(String userName);

    @Async
    @Query(nativeQuery = true , value = "select * from users u where u.user_name = :user_name")
    CompletableFuture<Users> findByUsers(
            @Param("user_name") String userName
    );

    @Query(nativeQuery = true , value = "select * from users u where u.user_name = :user_name")
    Users findByUsersSync(
            @Param("user_name") String userName
    );
}

