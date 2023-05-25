package by.academy.service.impl;

import by.academy.repository.BrandRepository;
import by.academy.service.BrandService;
import by.academy.service.dto.BrandDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createBrandDTO;
import static by.academy.util.TestConstants.TEST_BRAND_NAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class BrandServiceImplTest {

    private final BrandService brandService;
    private final BrandRepository brandRepository;

    @Autowired
    public BrandServiceImplTest(BrandService brandService, BrandRepository brandRepository) {
        this.brandService = brandService;
        this.brandRepository = brandRepository;
    }

    @Test
    void createBrand() {
        BrandDTO result = brandService.createBrand(createBrandDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(TEST_BRAND_NAME, result.getName());
    }

    @Test
    void readAllBrands() {
        List<BrandDTO> resultBeforeAddingNewBrands =
                brandService.findAllBrands();

        brandService.createBrand(createBrandDTO());
        brandService.createBrand(createBrandDTO());

        List<BrandDTO> resultAfterAddingNewBrands =
                brandService.findAllBrands();

        assertNotNull(resultBeforeAddingNewBrands);
        assertNotNull(resultAfterAddingNewBrands);
        assertEquals(TEST_BRAND_NAME,
                resultAfterAddingNewBrands.get(
                        resultAfterAddingNewBrands.size() - 1).getName());
        assertEquals(resultBeforeAddingNewBrands.size() + 2,
                resultAfterAddingNewBrands.size());
    }

    @Test
    void updateBrand() {
        BrandDTO BrandDTO = createBrandDTO();
        BrandDTO createdBrandDTO = brandService.createBrand(BrandDTO);

        BrandDTO updatedBrandDTO = brandService.updateBrand(1L, createdBrandDTO)
                .orElseThrow();

        assertNotNull(updatedBrandDTO);
        assertEquals(1L, updatedBrandDTO.getId());
        assertEquals(createdBrandDTO.getName(), updatedBrandDTO.getName());
    }

    @Test
    void deleteBrand() {
        BrandDTO savedBrand = brandService.createBrand(createBrandDTO());

        brandService.deleteBrand(savedBrand.getId());

        assertFalse(brandRepository.existsById(savedBrand.getId()));
    }

    @Test
    void selectBrandById() {
        BrandDTO savedBrand = brandService.createBrand(createBrandDTO());
        Optional<BrandDTO> result = brandService.findBrandById(savedBrand.getId());

        assertTrue(result.isPresent());

        BrandDTO BrandDTO = result.get();
        assertEquals(savedBrand.getId(), BrandDTO.getId());
        assertEquals(savedBrand.getName(), BrandDTO.getName());
    }
    
    @Test
    void findBrandById() {
        BrandDTO brandDTO = brandService.createBrand(createBrandDTO());

        BrandDTO foundBrandDTO = brandService.findBrandById(brandDTO.getId())
                .orElseThrow();

        assertNotNull(foundBrandDTO);
        assertEquals(TEST_BRAND_NAME, foundBrandDTO.getName());
    }
}