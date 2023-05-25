package by.academy.service.impl;

import by.academy.repository.ChocolateRepository;
import by.academy.service.ChocolateService;
import by.academy.service.dto.ChocolateDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createChocolateDTO;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChocolateServiceImplTest {

    private final ChocolateService chocolateService;
    private final ChocolateRepository chocolateRepository;

    @Autowired
    public ChocolateServiceImplTest(ChocolateService chocolateService, ChocolateRepository chocolateRepository) {
        this.chocolateService = chocolateService;
        this.chocolateRepository = chocolateRepository;
    }

    @Test
    void testCreateChocolate() {
        ChocolateDTO result = chocolateService.createChocolate(createChocolateDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(CHOCOLATE_PRICE, result.getPrice());
        assertEquals(CHOCOLATE_TYPE, result.getType());
        assertEquals(CHOCOLATE_WEIGHT, result.getWeight());
        assertEquals(CHOCOLATE_COMPOSITION, result.getComposition());
        assertEquals(TEST_BRAND_NAME, result.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, result.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, result.getSupplierName());
    }

    @Test
    void testReadAllChocolates() {
        List<ChocolateDTO> result =
                chocolateService.findAllChocolates();

        assertNotNull(result);
    }

    @Test
    void testUpdateChocolate() {
        ChocolateDTO chocolateDTO = createChocolateDTO();
        ChocolateDTO createdChocolateDTO = chocolateService.createChocolate(chocolateDTO);

        // should use existing in db id so updateChocolate can find it and update
        ChocolateDTO updatedChocolateDTO = chocolateService.updateChocolate(1L, createdChocolateDTO)
                .orElseThrow();

        assertNotNull(updatedChocolateDTO);
        assertEquals(1L, updatedChocolateDTO.getId());
        assertEquals(createdChocolateDTO.getPrice(), updatedChocolateDTO.getPrice());
        assertEquals(createdChocolateDTO.getType(), updatedChocolateDTO.getType());
        assertEquals(createdChocolateDTO.getWeight(), updatedChocolateDTO.getWeight());
        assertEquals(createdChocolateDTO.getComposition(), updatedChocolateDTO.getComposition());
    }

    @Test
    void testDeleteChocolate() {
        ChocolateDTO savedChocolate = chocolateService.createChocolate(createChocolateDTO());

        chocolateService.deleteChocolate(savedChocolate.getId());

        assertFalse(chocolateRepository.existsById(savedChocolate.getId()));
    }

    @Test
    void testSelectChocolateById() {
        ChocolateDTO savedChocolate = chocolateService.createChocolate(createChocolateDTO());
        Optional<ChocolateDTO> result = chocolateService.findChocolateById(savedChocolate.getId());

        assertTrue(result.isPresent());

        ChocolateDTO chocolateDTO = result.get();
        assertEquals(savedChocolate.getId(), chocolateDTO.getId());
        assertEquals(savedChocolate.getPrice(), chocolateDTO.getPrice());
        assertEquals(savedChocolate.getType(), chocolateDTO.getType());
        assertEquals(savedChocolate.getWeight(), chocolateDTO.getWeight());
        assertEquals(savedChocolate.getComposition(), chocolateDTO.getComposition());
    }

    @Test
    void findChocolateById() {
        ChocolateDTO chocolateDTO = chocolateService.createChocolate(createChocolateDTO());

        ChocolateDTO foundChocolateDTO = chocolateService.findChocolateById(chocolateDTO.getId())
                .orElseThrow();

        assertNotNull(foundChocolateDTO);
        assertEquals(CHOCOLATE_PRICE, foundChocolateDTO.getPrice());
        assertEquals(CHOCOLATE_TYPE, foundChocolateDTO.getType());
        assertEquals(CHOCOLATE_WEIGHT, foundChocolateDTO.getWeight());
        assertEquals(CHOCOLATE_COMPOSITION, foundChocolateDTO.getComposition());
        assertEquals(TEST_BRAND_NAME, foundChocolateDTO.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, foundChocolateDTO.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, foundChocolateDTO.getSupplierName());
    }
}