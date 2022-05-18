package ca.lebl.monitoring.service;

import ca.lebl.monitoring.entity.User;
import ca.lebl.monitoring.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User getUserByAccessToken(String accessToken) {
        return userRepository.findByAccessToken(accessToken);
    }

    public User createUser(String username, String email, String accessToken) {
        User newUser = new User(username, email, accessToken);

        userRepository.save(newUser);

        return newUser;
    }
}
