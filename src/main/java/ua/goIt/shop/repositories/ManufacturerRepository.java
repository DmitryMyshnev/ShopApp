package ua.goIt.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goIt.shop.model.Manufacturer;
@Repository
public interface ManufacturerRepository extends JpaRepository<Manufacturer,Long> {
    Manufacturer findByName(String name);
}
