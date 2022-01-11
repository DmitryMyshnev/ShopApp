package ua.goIt.shop.config.validation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.goIt.shop.model.Manufacturer;
import ua.goIt.shop.model.Product;
import ua.goIt.shop.model.User;
import ua.goIt.shop.services.ManufacturerService;
import ua.goIt.shop.services.ProductService;
import ua.goIt.shop.services.UserService;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class ExistValidator implements ConstraintValidator<IsExist, Object> {

    private UserService userService;

    private ManufacturerService manufacturerService;

    private ProductService productService;


    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value instanceof User) {
            User user = (User) value;
            if (userService.isExist(user)) {
                return false;
            }
        }
        if (value instanceof Manufacturer) {
            Manufacturer manufacturer = (Manufacturer) value;
            if (manufacturerService.isExist(manufacturer)) {
                return false;
            }
        }
        if (value instanceof Product) {
            Product product = (Product) value;
            if (productService.isExist(product)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void initialize(IsExist constraintAnnotation) {
        userService = ApplicationContextProvider.getContext().getBean(UserService.class);
        manufacturerService = ApplicationContextProvider.getContext().getBean(ManufacturerService.class);
        productService = ApplicationContextProvider.getContext().getBean(ProductService.class);
    }
}
