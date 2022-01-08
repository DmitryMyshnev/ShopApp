package ua.goIt.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goIt.shop.model.Role;
import ua.goIt.shop.repositories.RoleRepository;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(Long id) {
        return roleRepository.getById(id);
    }
}
