package ua.goIt.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.goIt.shop.model.Manufacturer;
import ua.goIt.shop.services.ManufacturerService;
import ua.goIt.shop.services.ProductService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Controller
@RequestMapping("/manufacturers")
public class ManufacturerController {

    @Autowired
    private ManufacturerService manufacturerService;

    @Autowired
    private ProductService productService;

    @GetMapping
    public String allManufacturer(Model model) {
        List<Manufacturer> allManufacturer = manufacturerService.getAllManufacturer();
        model.addAttribute("listManufacturer", allManufacturer);
        return "all_manufacturer";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable(value = "id") UUID id, Model model) {
        model.addAttribute("manufacturer", manufacturerService.getManufacturerById(id));
        return "edit_manufacturer";
    }

    @PostMapping("/update_manufacturer")
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    public String updateManufacturer(@ModelAttribute("manufacturer") @Valid Manufacturer manufacturer, BindingResult result, Model model) {
        if (result.hasFieldErrors()) {
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            return "edit_manufacturer";
        }
        String currentName = manufacturerService.getManufacturerById(manufacturer.getId()).getName();
        if (!manufacturer.getName().equals(currentName))
            if (manufacturerService.isExist(manufacturer)) {
                model.addAttribute("IsExist_name", true);
                return "edit_manufacturer";
            }
        manufacturerService.updateManufacturer(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/new_manufacturer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String newManufacturer(Model model) {
        model.addAttribute("manufacturer", new Manufacturer());
        return "new_manufacturer";
    }

    @PostMapping("/save_new_manufacturer")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String saveNewUser(@ModelAttribute("manufacturer") @Valid Manufacturer manufacturer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            result.getFieldErrors().forEach(err -> model.addAttribute(Objects.requireNonNull(err.getCode()) + "_" + err.getField(), true));
            return "new_manufacturer";
        }
        if (manufacturerService.isExist(manufacturer)) {
            model.addAttribute("IsExist_name", true);
            return "new_manufacturer";
        }
        manufacturerService.saveManufacturer(manufacturer);
        return "redirect:/manufacturers";
    }

    @GetMapping("/remove/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public String removeManufacturer(@PathVariable(value = "id") UUID id) {
        manufacturerService.deleteManufacturer(id);
        return "redirect:/manufacturers";
    }
}
