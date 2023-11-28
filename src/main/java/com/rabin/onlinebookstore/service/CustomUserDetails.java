//package com.rabin.onlinebookstore.service;
//
//import com.rabin.onlinebookstore.model.Users;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import java.util.Collection;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//    private Users users;
//
//    public CustomUserDetails(Users user) {
//        this.users = user;
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return Collections.singletonList(()-> "ROLE_" + users.getRole());
//    }
//
//    @Override
//    public String getPassword() {
//        return users.getPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return users.getUsername();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
