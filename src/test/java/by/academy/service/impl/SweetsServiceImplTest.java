package by.academy.service.impl;

import by.academy.repository.SweetsRepository;
import by.academy.service.SweetsService;
import by.academy.service.dto.SweetsDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createSweetsDTO;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SweetsServiceImplTest {

    private final SweetsService sweetsService;
    private final SweetsRepository sweetsRepository;

    @Autowired
    public SweetsServiceImplTest(SweetsService sweetsService, SweetsRepository sweetsRepository) {
        this.sweetsService = sweetsService;
        this.sweetsRepository = sweetsRepository;
    }

    @Test
    void testCreateSweets() {
        SweetsDTO result = sweetsService.createSweets(createSweetsDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(SWEETS_PRICE, result.getPrice());
        assertEquals(SWEETS_TYPE, result.getType());
        assertEquals(SWEETS_WEIGHT, result.getWeight());
        assertEquals(SWEETS_COMPOSITION, result.getComposition());
        assertEquals(TEST_BRAND_NAME, result.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, result.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, result.getSupplierName());
    }

    @Test
    void testReadAllSweets() {
        List<SweetsDTO> result =
                sweetsService.findAllSweets();

        assertNotNull(result);
    }

    @Test
    void testUpdateSweets() {
        SweetsDTO SweetsDTO = createSweetsDTO();
        SweetsDTO createdSweetsDTO = sweetsService.createSweets(SweetsDTO);

        // should use existing in db id so updateSweets can find it and update
        SweetsDTO updatedSweetsDTO = sweetsService.updateSweets(1L, createdSweetsDTO)
                .orElseThrow();

        assertNotNull(updatedSweetsDTO);
        assertEquals(1L, updatedSweetsDTO.getId());
        assertEquals(createdSweetsDTO.getPrice(), updatedSweetsDTO.getPrice());
        assertEquals(createdSweetsDTO.getType(), updatedSweetsDTO.getType());
        assertEquals(createdSweetsDTO.getWeight(), updatedSweetsDTO.getWeight());
        assertEquals(createdSweetsDTO.getComposition(), updatedSweetsDTO.getComposition());
    }

    @Test
    void testDeleteSweets() {
        SweetsDTO savedSweets = sweetsService.createSweets(createSweetsDTO());

        sweetsService.deleteSweets(savedSweets.getId());

        assertFalse(sweetsRepository.existsById(savedSweets.getId()));
    }

    @Test
    void testSelectSweetsById() {
        SweetsDTO savedSweets = sweetsService.createSweets(createSweetsDTO());
        Optional<SweetsDTO> result = sweetsService.findSweetsById(savedSweets.getId());

        assertTrue(result.isPresent());

        SweetsDTO SweetsDTO = result.get();
        assertEquals(savedSweets.getId(), SweetsDTO.getId());
        assertEquals(savedSweets.getPrice(), SweetsDTO.getPrice());
        assertEquals(savedSweets.getType(), SweetsDTO.getType());
        assertEquals(savedSweets.getWeight(), SweetsDTO.getWeight());
        assertEquals(savedSweets.getComposition(), SweetsDTO.getComposition());
    }

    @Test
    void findSweetsById() {
        SweetsDTO SweetsDTO = sweetsService.createSweets(createSweetsDTO());

        SweetsDTO foundSweetsDTO = sweetsService.findSweetsById(SweetsDTO.getId())
                .orElseThrow();

        assertNotNull(foundSweetsDTO);
        assertEquals(SWEETS_PRICE, foundSweetsDTO.getPrice());
        assertEquals(SWEETS_TYPE, foundSweetsDTO.getType());
        assertEquals(SWEETS_WEIGHT, foundSweetsDTO.getWeight());
        assertEquals(SWEETS_COMPOSITION, foundSweetsDTO.getComposition());
        assertEquals(TEST_BRAND_NAME, foundSweetsDTO.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, foundSweetsDTO.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, foundSweetsDTO.getSupplierName());
    }
}