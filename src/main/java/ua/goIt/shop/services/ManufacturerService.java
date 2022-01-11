package ua.goIt.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ua.goIt.shop.model.Manufacturer;
import ua.goIt.shop.model.User;
import ua.goIt.shop.repositories.ManufacturerRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ManufacturerService {
    @Autowired
    private ManufacturerRepository manufacturerRepository;

    public List<Manufacturer> getAllManufacturer(){
       return manufacturerRepository.findAll();
    }

    public Manufacturer getManufacturerById(UUID id){
        return manufacturerRepository.getById(id);
    }

    public void saveManufacturer(Manufacturer manufacturer){
        manufacturerRepository.save(manufacturer);
    }

    public void deleteManufacturer(UUID id){
        manufacturerRepository.deleteById(id);
    }

    public void updateManufacturer(Manufacturer manufacturer) {
        Manufacturer manufacturerWhoUpdate = getManufacturerById(manufacturer.getId());
        manufacturerWhoUpdate.setName(manufacturer.getName());
        manufacturerRepository.save(manufacturerWhoUpdate);
    }

    public boolean isExist(Manufacturer manufacturer){
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withIgnorePaths("id");
        Example<Manufacturer> example = Example.of(manufacturer,matcher);

        return manufacturerRepository.exists(example);
    }
}
