package ua.goIt.shop.model;

import lombok.Data;
import ua.goIt.shop.config.validation.IsExist;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
//@IsExist
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private UUID id;
    @NotEmpty
    private String name;
    @NotNull
    private BigDecimal price;
    @ManyToOne()
    private Manufacturer manufacturer;

    public Product(Manufacturer manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Product() {
    }
}
