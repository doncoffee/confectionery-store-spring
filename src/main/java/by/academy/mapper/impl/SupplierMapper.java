package by.academy.mapper.impl;

import by.academy.entity.Address;
import by.academy.entity.PhoneNumber;
import by.academy.entity.Supplier;
import by.academy.mapper.Mapper;
import by.academy.repository.AddressRepository;
import by.academy.repository.PhoneNumberRepository;
import by.academy.service.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SupplierMapper implements Mapper<Supplier, SupplierDTO> {
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public Supplier mapToEntity(SupplierDTO object) {
        return Supplier.builder()
                .id(object.getId())
                .name(object.getName())
                .contactPerson(object.getContactPerson())
                .address(getAddress(object.getAddressId()))
                .phoneNumber(getPhoneNumber(object.getPhoneNumberId()))
                .build();
    }

    @Override
    public SupplierDTO mapToDTO(Supplier object) {
        return SupplierDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .contactPerson(object.getContactPerson())
                .addressId(object.getAddress().getId())
                .addressName(object.getAddress().getName())
                .phoneNumberId(object.getPhoneNumber().getId())
                .phoneNumberNumber(object.getPhoneNumber().getNumber())
                .build();
    }

    @Override
    public Supplier map(SupplierDTO fromObject, Supplier toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(SupplierDTO supplierDTO, Supplier supplier) {
        supplier.setName(supplierDTO.getName());
        supplier.setContactPerson(supplierDTO.getContactPerson());
        supplier.setAddress(getAddress(supplierDTO.getAddressId()));
        supplier.setPhoneNumber(getPhoneNumber(supplierDTO.getPhoneNumberId()));
    }

    private Address getAddress(Long addressId) {
        return Optional.ofNullable(addressId)
                .flatMap(addressRepository::findById)
                .orElse(null);
    }

    private PhoneNumber getPhoneNumber(Long phoneNumberId) {
        return Optional.ofNullable(phoneNumberId)
                .flatMap(phoneNumberRepository::findById)
                .orElse(null);
    }
}
