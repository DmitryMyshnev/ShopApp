package ua.goIt.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.goIt.shop.model.Manufacturer;
import ua.goIt.shop.model.Product;
import ua.goIt.shop.services.ManufacturerService;
import ua.goIt.shop.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private final ManufacturerService manufacturerService;

    private  List<Manufacturer> listManufacturer;
    @Autowired
    public ProductController(ManufacturerService manufacturerService) {
        this.manufacturerService = manufacturerService;
        listManufacturer = manufacturerService.getAllManufacturer();
    }

    @GetMapping
    public String allProduct(Model model) {
        List<Product> allProduct = productService.getAllProduct();
        listManufacturer = manufacturerService.getAllManufacturer();
        model.addAttribute("listProduct", allProduct);
        return "all_product";
    }

    @GetMapping("/new_product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newProduct(Model model) {
        model.addAttribute("product", new Product(new Manufacturer()));
        model.addAttribute("allManufacturer", listManufacturer);
        return "new_product";
    }

    @PostMapping("/save_new_product")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveNewProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model) {
         if(product.getManufacturer().getId() == null){
             result.addError(new FieldError("manufacturer", "manufacturerId", product, false, new String[]{"NotNull"}, null, null));
         }
        if (result.hasErrors()) {
            model.addAttribute("allManufacturer", listManufacturer);
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            return "new_product";
        }
        if (productService.isExist(product)) {
            model.addAttribute("IsExist_name", true);
            model.addAttribute("allManufacturer", listManufacturer);
            return "new_product";
        }
        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editProduct(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("product", productService.getProductById(id));
        model.addAttribute("allManufacturer", listManufacturer);
        return "edit_product";
    }

    @PostMapping("/update_product")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String updateProduct(@ModelAttribute("product") @Valid Product product, BindingResult result, Model model) {
        Product currentProduct = productService.getProductById(product.getId());
        if (result.hasFieldErrors()) {
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            currentProduct.setPrice(null);
            model.addAttribute("product", currentProduct);
            model.addAttribute("allManufacturer", listManufacturer);
            return "edit_product";
        }
        if (!product.getName().equals(currentProduct.getName()))
            if (productService.isExist(product)) {
                model.addAttribute("IsExist_name", true);
                currentProduct.setName(product.getName());
                model.addAttribute("product", currentProduct);
                model.addAttribute("allManufacturer", listManufacturer);
                return "edit_product";
            }

        productService.saveProduct(product);
        return "redirect:/products";
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeProduct(@PathVariable(value = "id") UUID id) {
        productService.deleteProduct(id);
        return "redirect:/products";
    }
}
