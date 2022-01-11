package ua.goIt.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.goIt.shop.model.User;
import ua.goIt.shop.repositories.UserRepository;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserBiId(UUID id) {
        return userRepository.getById(id);
    }

    public User getUserByEmail(String email){
      return   userRepository.findUserByEmail(email);
    }

    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    public void updateUser(User user) {
        User userWhoUpdate = getUserBiId(user.getId());
        userWhoUpdate.setEmail(user.getEmail());
        userWhoUpdate.setFirstName(user.getFirstName());
        userWhoUpdate.setLastName(user.getLastName());
        userRepository.save(userWhoUpdate);
    }

    public void updatePassword(User user) {
        User userWhoUpdate = getUserBiId(user.getId());
        userWhoUpdate.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(userWhoUpdate);
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
    }

    public boolean isExist(User user){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("password")
                .withIgnorePaths("firstName")
                .withIgnorePaths("lastName")
                .withIgnorePaths("role");
        Example<User> example = Example.of(user,matcher);

        return userRepository.exists(example);
    }

}
