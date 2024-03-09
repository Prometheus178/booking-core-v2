package org.booking.core.domain.entity.user;

import jakarta.persistence.*;
import lombok.*;
import org.booking.core.domain.entity.role.Role;
import org.booking.core.domain.entity.base.AbstractEntity;
import org.booking.core.domain.entity.user.history.UserReservationHistory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

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
    private String password;
    private String salt;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private UserReservationHistory reservationHistory;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(
                role -> authorities.add(new SimpleGrantedAuthority(role.getName()))
        );
        return authorities;
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
