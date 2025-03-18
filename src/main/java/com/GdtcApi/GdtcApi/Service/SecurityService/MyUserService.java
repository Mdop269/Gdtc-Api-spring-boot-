package com.GdtcApi.GdtcApi.Service.SecurityService;

import com.GdtcApi.GdtcApi.SecuirtyModels.UserPrincipal;
import com.GdtcApi.GdtcApi.Entities.Users;
import com.GdtcApi.GdtcApi.Repsoitory.UsersRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

//UserdetailService implementation to load user credentials from the master db
@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    UsersRepo usersRepo;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Users user = usersRepo.findByUsersSync(userName);

        if(user == null){
            System.out.println("Uer Not Found");
            throw new UsernameNotFoundException("user not found");
        }
        //Custom UserPrincipal wrapping our users entity
        return new UserPrincipal(user);
    }
}
