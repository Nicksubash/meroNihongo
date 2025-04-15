package com.mero_nihongo.meroNihongo.security;

import com.mero_nihongo.meroNihongo.model.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

@Getter
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Long getId() {
        return user.getId();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // No roles/authorities yet, return empty
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Can customize if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Can customize if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Can customize if needed
    }

    @Override
    public boolean isEnabled() {
        return true; // Can customize if needed
    }
}
