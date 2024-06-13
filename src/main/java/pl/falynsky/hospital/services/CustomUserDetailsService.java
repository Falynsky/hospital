package pl.falynsky.hospital.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.falynsky.hospital.entities.Privilege;
import pl.falynsky.hospital.entities.Role;
import pl.falynsky.hospital.entities.Users;
import pl.falynsky.hospital.repositories.RoleRepository;
import pl.falynsky.hospital.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Service("userDetailsService")
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Users user = userRepository.findByEmail(email);
        if (user == null) {
            Role role = roleRepository.findByName("ROLE_USER");
            return new User(
                    " ",
                    " ",
                    true,
                    true,
                    true,
                    true,
                    getAuthorities(Collections.singletonList(role))
            );
        }

        return new User(
                user.getEmail(),
                user.getPassword(),
                user.isEnabled(),
                true,
                true,
                true,
                getAuthorities(user.getRoles())
        );
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
