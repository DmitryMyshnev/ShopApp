package ua.goIt.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ua.goIt.shop.model.User;


@Repository
public interface UserRepository extends JpaRepository<User,Long> {
   // @Query("SELECT u FROM User u WHERE u.email = ?1")

     User findUserByEmail(String email);
}
