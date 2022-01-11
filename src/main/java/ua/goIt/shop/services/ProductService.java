package ua.goIt.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import ua.goIt.shop.model.Manufacturer;
import ua.goIt.shop.model.Product;
import ua.goIt.shop.repositories.ProductRepository;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProduct(){
        return productRepository.findAll();
    }

    public Product getProductById(UUID id){
        return productRepository.getById(id);
    }

    public void saveProduct(Product product){
        productRepository.save(product);
    }

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    public boolean isExist(Product product) {
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id")
                .withIgnorePaths("price")
                .withIgnorePaths("manufacturer");
        Example<Product> example = Example.of(product,matcher);
        return productRepository.exists(example);
    }

    public void updateProduct(Product product) {

        productRepository.save(product);
    }
}

