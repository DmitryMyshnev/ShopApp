package ua.goIt.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.goIt.shop.model.Role;
import ua.goIt.shop.repositories.RoleRepository;

import java.util.List;
import java.util.UUID;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    public List<Role> getAll() {
        return roleRepository.findAll();
    }

    public Role getById(UUID id) {
        return roleRepository.getById(id);
    }
}
