package ua.goIt.shop.model;

import lombok.Data;
import ua.goIt.shop.config.validation.IsExist;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.List;
import java.util.Set;

@Data
@Entity
//@IsExist
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition ="serial")
    private Long id;
    @NotEmpty
    private String name;
    @OneToMany(mappedBy = "manufacturer", cascade = CascadeType.ALL)
    private List<Product> products;
}
