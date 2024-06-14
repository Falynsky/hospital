package pl.falynsky.hospital.services;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.falynsky.hospital.entities.User;
import pl.falynsky.hospital.repositories.UserRepository;

@RequiredArgsConstructor
@Service()
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password.");
        }

        //TODO (Falynsky): remove this walkaround after separating UserDetail from User
        int size = user.getAuthorities().size();
        return user;
    }
}
