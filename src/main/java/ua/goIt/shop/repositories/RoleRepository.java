package ua.goIt.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goIt.shop.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
}
