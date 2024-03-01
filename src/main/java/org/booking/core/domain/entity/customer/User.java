package org.booking.core.domain.entity.customer;

import lombok.*;
import org.booking.core.domain.entity.Role;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@Entity(name = User.ENTITY_NAME)
@Table(name = User.TABLE_NAME)
@NoArgsConstructor
@AllArgsConstructor
public class User extends AbstractEntity implements UserDetails {

    public static final String ENTITY_NAME = "User";
    public static final String TABLE_NAME = "users";

    private String name;
    private String email;
    @Enumerated(value = EnumType.STRING)
    private Role role;
    private String password;
    private String salt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return !deleted;
    }

}
