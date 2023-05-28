package by.academy.service.impl;

import by.academy.repository.StoreRepository;
import by.academy.service.StoreService;
import by.academy.service.dto.StoreDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createStoreDTO;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class StoreServiceImplTest {

    private final StoreService storeService;
    private final StoreRepository storeRepository;

    @Autowired
    public StoreServiceImplTest(StoreService storeService, StoreRepository storeRepository) {
        this.storeService = storeService;
        this.storeRepository = storeRepository;
    }

    @Test
    void createStore() {
        StoreDTO result = storeService.createStore(createStoreDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(TEST_ADDRESS_NAME, result.getAddressName());
        assertEquals(NUMBER, result.getPhoneNumberNumber());
    }

    @Test
    void readAllStores() {
        List<StoreDTO> result = storeService.findAllStores();
        assertNotNull(result);
    }

    @Test
    void updateStore() {
        StoreDTO StoreDTO = createStoreDTO();
        StoreDTO createdStoreDTO = storeService.createStore(StoreDTO);

        StoreDTO updatedStoreDTO = storeService.updateStore(1L, createdStoreDTO)
                .orElseThrow();

        assertNotNull(updatedStoreDTO);
        assertEquals(1L, updatedStoreDTO.getId());
        assertEquals(createdStoreDTO.getAddressName(), updatedStoreDTO.getAddressName());
        assertEquals(createdStoreDTO.getPhoneNumberNumber(), updatedStoreDTO.getPhoneNumberNumber());
    }

    @Test
    void deleteStore() {
        StoreDTO savedStore = storeService.createStore(createStoreDTO());

        storeService.deleteStore(savedStore.getId());

        assertFalse(storeRepository.existsById(savedStore.getId()));
    }

    @Test
    void selectStoreById() {
        StoreDTO savedStore = storeService.createStore(createStoreDTO());
        Optional<StoreDTO> result = storeService.findStoreById(savedStore.getId());

        assertTrue(result.isPresent());

        StoreDTO StoreDTO = result.get();
        assertEquals(savedStore.getId(), StoreDTO.getId());
        assertEquals(savedStore.getAddressName(), StoreDTO.getAddressName());
        assertEquals(savedStore.getPhoneNumberNumber(), StoreDTO.getPhoneNumberNumber());
    }

    @Test
    void findStoreById() {
        StoreDTO storeDTO = storeService.createStore(createStoreDTO());

        StoreDTO foundStoreDTO = storeService.findStoreById(storeDTO.getId())
                .orElseThrow();

        assertNotNull(foundStoreDTO);
        assertEquals(TEST_ADDRESS_NAME, foundStoreDTO.getAddressName());
        assertEquals(NUMBER, foundStoreDTO.getPhoneNumberNumber());
    }
}