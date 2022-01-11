package ua.goIt.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ua.goIt.shop.model.Role;
import ua.goIt.shop.model.User;
import ua.goIt.shop.services.RoleService;
import ua.goIt.shop.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    private final List<Role> roles;

    @Autowired
    public UserController(RoleService roleService) {
        roles = roleService.getAll();
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public String allUsers(Model model,Authentication authentication) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("listUsers", allUsers);
        model.addAttribute("currentUser", authentication.getName());
        return "all_users";
    }

    @GetMapping("/user_detail/{id}")
    public String userDetail(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("user_detail", userService.getUserBiId(id));
        return "user_detail";
    }

    @PostMapping("/save_new_user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveNewUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if (user.getRoles().stream().allMatch(role -> role.getId() == null)) {
            result.addError(new FieldError("role", "roleId", user, false, new String[]{"NotNull"}, null, null));
        }
        if (result.hasErrors()) {
            model.addAttribute("allRole", roles);
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            return "new_user";
        }
        if (userService.isExist(user)) {
            model.addAttribute("IsExist_email", true);
            model.addAttribute("allRole", roles);
            return "new_user";
        }
        userService.saveUser(user);
        return "redirect:/users";
    }

    @GetMapping("/new_user")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newUser(Model model) {
        model.addAttribute("user", new User(new ArrayList<>()));
        model.addAttribute("allRole", roles);
        return "new_user";
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeUser(@PathVariable(value = "id") UUID id) {
        userService.deleteUserById(id);
        return "redirect:/users";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("user", userService.getUserBiId(id));
        model.addAttribute("allRole", roles);
        return "edit_user";
    }

    @PostMapping("/update_user")
    public String updateUser(@ModelAttribute("user") @Valid User user, BindingResult result, Model model, Authentication authentication) {
        AtomicBoolean hasError = new AtomicBoolean(false);
        if (result.hasErrors()) {
            result.getFieldErrors().stream()
                    .filter(f -> (f.getField().equals("email") || f.getField().equals("firstName")))
                    .forEach(err -> {
                        model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true);
                        hasError.set(true);
                    });

            if (hasError.get())
                return "edit_user";
        }
        User currentUser = userService.getUserByEmail(authentication.getName());
        String currentEmail = currentUser.getEmail();
        UUID currentId = currentUser.getId();
        if (!user.getId().equals(currentId)) {
            if (userService.isExist(user)) {
                model.addAttribute("IsExist_email", true);
                return "edit_user";
            }
        } else {
            if (!user.getEmail().equals(currentEmail)) {
                if (userService.isExist(user)) {
                    model.addAttribute("IsExist_email", true);
                    return "edit_user";
                }
                userService.updateUser(user);
                return "redirect:/logout";
            }
        }
        userService.updateUser(user);
        if (authentication.getAuthorities().stream().anyMatch(role -> role.getAuthority().equals("ADMIN")))
            return "redirect:/users";
        else
            return "redirect:/products";
    }

    @GetMapping("/edit/edit_password/{id}")
    public String updatePassword(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("user", userService.getUserBiId(id));
        return "edit_password";
    }

    @PostMapping("/update_password")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String updatePassword(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        AtomicBoolean hasError = new AtomicBoolean(false);
        if (result.hasErrors()) {
            result.getFieldErrors().stream()
                    .filter(f -> (f.getField().equals("password")))
                    .forEach(err -> {
                        model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true);
                        hasError.set(true);
                    });

            if (hasError.get())
                return "edit_password";
        }
        userService.updatePassword(user);
        return "redirect:/logout";
    }
}
