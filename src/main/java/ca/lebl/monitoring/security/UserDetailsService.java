package ca.lebl.monitoring.security;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String accessToken) throws UsernameNotFoundException {
        User user = userRepository.findByAccessToken(accessToken);

        if (user == null) {
            throw new UsernameNotFoundException("No user found for this token");
        }

        return new UserDetails(user);
    }
}
