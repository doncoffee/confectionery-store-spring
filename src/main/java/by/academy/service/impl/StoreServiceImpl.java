package by.academy.service.impl;

import by.academy.entity.Address;
import by.academy.entity.PhoneNumber;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.mapper.impl.StoreMapper;
import by.academy.repository.AddressRepository;
import by.academy.repository.PhoneNumberRepository;
import by.academy.repository.StoreRepository;
import by.academy.service.StoreService;
import by.academy.service.dto.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;
    private final AddressRepository addressRepository;
    private final PhoneNumberRepository phoneNumberRepository;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO) {
        return Optional.of(storeDTO)
                .map(dto -> {
                    Store store = new Store();
                    copy(dto, store);
                    return store;
                })
                .map(storeRepository::save)
                .map(storeMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<StoreDTO> findAllStores(String search, Pageable pageable) {
        return (search == null) ?
                storeRepository.findAllByOrderById(pageable)
                        .map(storeMapper::mapToDTO) :
                storeRepository.findAllBySearchAndPage(search, pageable)
                        .map(storeMapper::mapToDTO);
    }

    @Override
    public List<StoreDTO> findAllStores() {
        return storeRepository.findAll().stream()
                .map(storeMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<StoreDTO> updateStore(Long id, StoreDTO storeDTO) {
        return storeRepository.findById(id)
                .map(entity -> copy(storeDTO, entity))
                .map(storeRepository::save)
                .map(storeMapper::mapToDTO);
    }

    @Override
    public boolean deleteStore(Long id) {
        return storeRepository.findById(id)
                .map(entity -> {
                    storeRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<StoreDTO> findStoreById(Long id) {
        return storeRepository.findById(id)
                .map(storeMapper::mapToDTO);
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

    private Store copy(StoreDTO storeDTO, Store store) {
        store.setId(storeDTO.getId());
        store.setAddress(getAddress(storeDTO.getAddressId()));
        store.setPhoneNumber(getPhoneNumber(storeDTO.getPhoneNumberId()));
        return store;
    }
}
