package by.academy.mapper.impl;

import by.academy.entity.Address;
import by.academy.entity.PhoneNumber;
import by.academy.entity.Store;
import by.academy.mapper.Mapper;
import by.academy.repository.AddressRepository;
import by.academy.repository.PhoneNumberRepository;
import by.academy.service.AddressService;
import by.academy.service.dto.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StoreMapper implements Mapper<Store, StoreDTO> {
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public Store mapToEntity(StoreDTO object) {
        return Store.builder()
                .id(object.getId())
                .address(getAddress(object.getAddressId()))
                .phoneNumber(getPhoneNumber(object.getPhoneNumberId()))
                .build();
    }

    @Override
    public StoreDTO mapToDTO(Store object) {
        return StoreDTO.builder()
                .id(object.getId())
                .addressId(object.getAddress().getId())
                .addressName(object.getAddress().getName())
                .phoneNumberId(object.getPhoneNumber().getId())
                .phoneNumberNumber(object.getPhoneNumber().getNumber())
                .build();
    }

    @Override
    public Store map(StoreDTO fromObject, Store toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(StoreDTO storeDTO, Store store) {
        store.setAddress(getAddress(storeDTO.getAddressId()));
        store.setPhoneNumber(getPhoneNumber(storeDTO.getPhoneNumberId()));
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
