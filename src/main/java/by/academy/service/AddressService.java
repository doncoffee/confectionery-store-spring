package by.academy.service;

import by.academy.service.dto.AddressDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface AddressService {

    AddressDTO createAddress(AddressDTO addressDTO);

    Page<AddressDTO> findAllAddresses(String search, Pageable pageable);

    List<AddressDTO> findAllAddresses();

    Optional<AddressDTO> updateAddress(Long id, AddressDTO addressDTO);

    boolean deleteAddress(Long id);

    Optional<AddressDTO> findAddressById(Long id);
}
