package by.academy.service;

import by.academy.service.dto.StoreDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface StoreService {

    StoreDTO createStore(StoreDTO storeDTO);

    Page<StoreDTO> findAllStores(Pageable pageable);
    List<StoreDTO> findAllStores();

    Optional<StoreDTO> updateStore(Long id, StoreDTO storeDTO);

    boolean deleteStore(Long id);

    Optional<StoreDTO> findStoreById(Long id);
}
