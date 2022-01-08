package ua.goIt.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.goIt.shop.model.Role;
import ua.goIt.shop.model.User;
import ua.goIt.shop.services.RoleService;
import ua.goIt.shop.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

@Controller
public class HomeController {
    @Autowired
    private UserService userService;

    private final List<Role> roles;
    @Autowired
    public HomeController(RoleService roleService) {
        roles = roleService.getAll();
    }

    @GetMapping("/")
    public String index() {
        return "redirect:/products";
    }


    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "login";
    }

    @PostMapping("/logout")
    public String logout() {
        return "login";
    }

    @GetMapping("/registry")
    public String registry(Model model) {
        model.addAttribute("user", new User(new Role()));
        model.addAttribute("allRole", roles);
        return "registry";
    }

    @PostMapping("/process_register")
    public String processRegister(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
        if(user.getRole().getId() == null){
            result.addError(new FieldError("role","roleId",user,false, new String[]{"NotNull"},null,null));
        }
        if (result.hasErrors()) {
            model.addAttribute("allRole", roles);
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            return "registry";
        }
        if(userService.isExist(user)) {
            model.addAttribute("IsExist_email", true);
            model.addAttribute("allRole", roles);
            return "registry";
        }
        userService.saveUser(user);
        model.addAttribute("successful",true);
        return "login";
    }

}
