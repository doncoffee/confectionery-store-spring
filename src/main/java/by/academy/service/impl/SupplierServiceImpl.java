package by.academy.service.impl;

import by.academy.entity.Address;
import by.academy.entity.PhoneNumber;
import by.academy.entity.Supplier;
import by.academy.mapper.impl.SupplierMapper;
import by.academy.repository.AddressRepository;
import by.academy.repository.PhoneNumberRepository;
import by.academy.repository.SupplierRepository;
import by.academy.service.SupplierService;
import by.academy.service.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public SupplierDTO createSupplier(SupplierDTO supplierDTO) {
        return Optional.of(supplierDTO)
                .map(dto -> {
                    Supplier supplier = new Supplier();
                    copy(dto, supplier);
                    return supplier;
                })
                .map(supplierRepository::save)
                .map(supplierMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<SupplierDTO> findAllSuppliers(String search, Pageable pageable) {
        return (search == null) ?
                supplierRepository.findAllByOrderById(pageable)
                        .map(supplierMapper::mapToDTO) :
                supplierRepository.findAllBySearchAndPage(search, pageable)
                        .map(supplierMapper::mapToDTO);

    }

    @Override
    public List<SupplierDTO> findAllSuppliers() {
        return supplierRepository.findAll().stream()
                .map(supplierMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<SupplierDTO> updateSupplier(Long id,
                                                SupplierDTO supplierDTO) {
        return supplierRepository.findById(id)
                .map(entity -> copy(supplierDTO, entity))
                .map(supplierRepository::save)
                .map(supplierMapper::mapToDTO);
    }

    @Override
    public boolean deleteSupplier(Long id) {
        return supplierRepository.findById(id)
                .map(entity -> {
                    supplierRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<SupplierDTO> findSupplierById(Long id) {
        return supplierRepository.findById(id)
                .map(supplierMapper::mapToDTO);
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

    private Supplier copy(SupplierDTO supplierDTO, Supplier supplier) {
        supplier.setName(supplierDTO.getName());
        supplier.setContactPerson(supplierDTO.getContactPerson());
        supplier.setAddress(getAddress(supplierDTO.getAddressId()));
        supplier.setPhoneNumber(getPhoneNumber(supplierDTO.getPhoneNumberId()));
        return supplier;
    }
}
