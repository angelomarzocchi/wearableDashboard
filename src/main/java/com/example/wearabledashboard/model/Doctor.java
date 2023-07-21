package com.example.wearabledashboard.model;


import com.example.wearabledashboard.model.enumerations.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.List;

@Entity
@Data
public class Doctor extends Person implements UserDetails {


    private String specialization;


    private String hashedPassword;


    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany
    private List<Patient> patients;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return this.hashedPassword;
    }


    @Override
    public String getUsername() {
        return this.getSsn();
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
