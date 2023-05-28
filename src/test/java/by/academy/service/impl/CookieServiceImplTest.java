package by.academy.service.impl;

import by.academy.repository.CookieRepository;
import by.academy.service.CookieService;
import by.academy.service.dto.CookieDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createCookieDTO;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class CookieServiceImplTest {
    private final CookieService cookieService;
    private final CookieRepository cookieRepository;

    @Autowired
    public CookieServiceImplTest(CookieService cookieService, CookieRepository cookieRepository) {
        this.cookieService = cookieService;
        this.cookieRepository = cookieRepository;
    }

    @Test
    void testCreateCookie() {
        CookieDTO result = cookieService.createCookie(createCookieDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(COOKIE_PRICE, result.getPrice());
        assertEquals(COOKIE_TYPE, result.getType());
        assertEquals(COOKIE_WEIGHT, result.getWeight());
        assertEquals(COOKIE_COMPOSITION, result.getComposition());
        assertEquals(TEST_BRAND_NAME, result.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, result.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, result.getSupplierName());
    }

    @Test
    void testReadAllCookies() {
        List<CookieDTO> result = cookieService.findAllCookies();
        assertNotNull(result);
    }

    @Test
    void testUpdateCookie() {
        CookieDTO CookieDTO = createCookieDTO();
        CookieDTO createdCookieDTO = cookieService.createCookie(CookieDTO);

        // should use existing in db id so updateCookie can find it and update
        CookieDTO updatedCookieDTO = cookieService.updateCookie(1L, createdCookieDTO)
                .orElseThrow();

        assertNotNull(updatedCookieDTO);
        assertEquals(1L, updatedCookieDTO.getId());
        assertEquals(createdCookieDTO.getPrice(), updatedCookieDTO.getPrice());
        assertEquals(createdCookieDTO.getType(), updatedCookieDTO.getType());
        assertEquals(createdCookieDTO.getWeight(), updatedCookieDTO.getWeight());
        assertEquals(createdCookieDTO.getComposition(), updatedCookieDTO.getComposition());
    }

    @Test
    void testDeleteCookie() {
        CookieDTO savedCookie = cookieService.createCookie(createCookieDTO());

        cookieService.deleteCookie(savedCookie.getId());

        assertFalse(cookieRepository.existsById(savedCookie.getId()));
    }

    @Test
    void testSelectCookieById() {
        CookieDTO savedCookie = cookieService.createCookie(createCookieDTO());
        Optional<CookieDTO> result = cookieService.findCookieById(savedCookie.getId());

        assertTrue(result.isPresent());

        CookieDTO CookieDTO = result.get();
        assertEquals(savedCookie.getId(), CookieDTO.getId());
        assertEquals(savedCookie.getPrice(), CookieDTO.getPrice());
        assertEquals(savedCookie.getType(), CookieDTO.getType());
        assertEquals(savedCookie.getWeight(), CookieDTO.getWeight());
        assertEquals(savedCookie.getComposition(), CookieDTO.getComposition());
    }

    @Test
    void findCookieById() {
        CookieDTO CookieDTO = cookieService.createCookie(createCookieDTO());

        CookieDTO foundCookieDTO = cookieService.findCookieById(CookieDTO.getId())
                .orElseThrow();

        assertNotNull(foundCookieDTO);
        assertEquals(COOKIE_PRICE, foundCookieDTO.getPrice());
        assertEquals(COOKIE_TYPE, foundCookieDTO.getType());
        assertEquals(COOKIE_WEIGHT, foundCookieDTO.getWeight());
        assertEquals(COOKIE_COMPOSITION, foundCookieDTO.getComposition());
        assertEquals(TEST_BRAND_NAME, foundCookieDTO.getBrandName());
        assertEquals(TEST_ADDRESS_NAME, foundCookieDTO.getStoreName());
        assertEquals(SUPPLIER_TEST_NAME, foundCookieDTO.getSupplierName());
    }
}