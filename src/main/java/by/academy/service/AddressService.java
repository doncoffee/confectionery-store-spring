package by.academy.service;

import by.academy.service.dto.AddressDTO;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    List<AddressDTO> findAllAddresses();

    Optional<AddressDTO> updateAddress(Long id, AddressDTO addressDTO);

    boolean deleteAddress(Long id);

    Optional<AddressDTO> findAddressById(Long id);
}
