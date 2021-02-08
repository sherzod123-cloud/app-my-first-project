package uz.real.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import uz.real.entity.Role;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import java.util.Collection;
import java.util.List;

public class UserDetailsImpl implements UserDetails {

    private String fullName;

    private String username;

    private String password;

    private Integer age;

    private List<GrantedAuthority> grantedAuthorities;

    public UserDetailsImpl(String full_name, String username, String password, Integer age, List<GrantedAuthority> grantedAuthorities) {
        this.fullName = full_name;
        this.username = username;
        this.password = password;
        this.age = age;
        this.grantedAuthorities = grantedAuthorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
