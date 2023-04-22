package by.academy.service;

import by.academy.service.dto.StoreDTO;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO);

    List<StoreDTO> findAllStores();

    Optional<StoreDTO> updateStore(Long id, StoreDTO storeDTO);

    boolean deleteStore(Long id);

    Optional<StoreDTO> findStoreById(Long id);
}
