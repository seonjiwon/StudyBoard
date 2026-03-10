package dev.jpa.studyboard.domain.user;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    public int createUser(String name) {
        User user = new User(name);
        userRepository.save(user);
        return user.getId();
    }

    public User findUser(int id) {
        return userRepository.findById(id);
    }


}
