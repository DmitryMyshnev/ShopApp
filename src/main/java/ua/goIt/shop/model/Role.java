package ua.goIt.shop.model;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Entity

public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition ="serial")
    private Long id;
    private String name;
}
