package ua.goIt.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.goIt.shop.model.Product;
@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {
    Product findByName(String name);
}
