package pl.falynsky.hospital.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity()
@Table(name = "users")
//TODO (Falynsky): separate the User entity from UserDetails and also use DTO Object by adding a new class to implement it to https://stackoverflow.com/questions/61675882/lazy-initialization-manytomany-what-is-the-error?rq=3
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    @SequenceGenerator(name = "user_seq", sequenceName = "user_seq", allocationSize = 1)
    private Long id;

    @Column(name = "firstname")
    private String firstName;
    @Column(name = "lastname")
    private String lastName;
    private String email;
    private String password;
    private boolean enabled;
    @Column(name = "tokenexpired")
    private boolean tokenExpired;

    @ManyToMany
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id")
    )
    private Collection<Role> roles;

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return getAuthorities(getRoles());
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Collection<Role> roles) {
        List<String> privileges = getPrivileges(roles);
        return getGrantedAuthorities(privileges);
    }

    private List<String> getPrivileges(Collection<Role> roles) {
        List<String> privileges = new ArrayList<>();
        List<Privilege> collection = new ArrayList<>();

        for (Role role : roles) {
            String name = role.getName();
            privileges.add(name);
            Collection<Privilege> rolePrivileges = role.getPrivileges();
            collection.addAll(rolePrivileges);
        }

        for (Privilege item : collection) {
            String name = item.getName();
            privileges.add(name);
        }

        return privileges;
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<String> privileges) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (String privilege : privileges) {
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(privilege);
            authorities.add(grantedAuthority);
        }

        return authorities;
    }
}