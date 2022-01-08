package ua.goIt.shop.model;

import lombok.Data;
import ua.goIt.shop.config.validation.IsExist;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
@Entity
//@IsExist
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(columnDefinition = "serial")
    private Long id;
    @Column(unique = true)
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    @Column(name = "first_name")
    @NotEmpty
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @ManyToOne()
    private Role role;

    public User(Role role) {
        this.role = role;
    }

    public User() {
    }
}
