package by.academy.mapper.impl;

import by.academy.entity.Address;
import by.academy.mapper.Mapper;
import by.academy.service.dto.AddressDTO;
import org.springframework.stereotype.Component;

@Component
public class AddressMapper implements Mapper<Address, AddressDTO> {
    @Override
    public Address mapToEntity(AddressDTO object) {
        return Address.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public AddressDTO mapToDTO(Address object) {
        return AddressDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public Address map(AddressDTO fromObject, Address toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(AddressDTO addressDTO, Address address) {
        address.setName(addressDTO.getName());
    }
}
