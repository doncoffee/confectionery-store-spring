package by.academy.service.impl;

import by.academy.mapper.impl.AddressMapper;
import by.academy.repository.AddressRepository;
import by.academy.service.AddressService;
import by.academy.service.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    @Override
    public AddressDTO createAddress(AddressDTO addressDTO) {
        return Optional.of(addressDTO)
                .map(addressMapper::mapToEntity)
                .map(addressRepository::save)
                .map(addressMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<AddressDTO> findAllAddresses(Pageable pageable) {
        return addressRepository.findAll(pageable)
                .map(addressMapper::mapToDTO);
    }

    @Override
    public List<AddressDTO> findAllAddresses() {
        return addressRepository.findAll().stream()
                .map(addressMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<AddressDTO> updateAddress(Long id, AddressDTO addressDTO) {
        return addressRepository.findById(id)
                .map(entity -> addressMapper.map(addressDTO, entity))
                .map(addressRepository::save)
                .map(addressMapper::mapToDTO);
    }

    @Override
    public boolean deleteAddress(Long id) {
        return addressRepository.findById(id)
                .map(entity -> {
                    addressRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<AddressDTO> findAddressById(Long id) {
        return addressRepository.findById(id)
                .map(addressMapper::mapToDTO);
    }
}
