package ua.goIt.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goIt.shop.model.User;

import java.util.UUID;


@Repository
public interface UserRepository extends JpaRepository<User, UUID> {

     User findUserByEmail(String email);
}
