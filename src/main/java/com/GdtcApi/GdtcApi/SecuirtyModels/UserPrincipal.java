package com.GdtcApi.GdtcApi.SecuirtyModels;

import com.GdtcApi.GdtcApi.Entities.Users;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

// in spring security UserPrincipal Refers to Current user so thats why i named this
public class UserPrincipal implements UserDetails {

    private Users users;

    public UserPrincipal(Users users){
        this.users = users;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("USER")); // we are defining each user has role of user
    }

    @Override
    public String getPassword() {
        return users.getPassword();
    }

    @Override
    public String getUsername() {
        return users.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // hard coded
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // hard coded
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // hard coded
    }

    @Override
    public boolean isEnabled() {
        return true; // hard coded
    }
}
