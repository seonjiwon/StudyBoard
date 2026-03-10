package dev.jpa.studyboard.domain.user;

import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public int createUser(String name) {
        User user = new User(name);
        userRepository.save(user);
        return user.getUserId();
    }

    public User findUser(int id) {
        return userRepository.findById(id);
    }


}
