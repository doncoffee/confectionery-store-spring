package by.academy.service.impl;

import by.academy.repository.SupplierRepository;
import by.academy.service.SupplierService;
import by.academy.service.dto.SupplierDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createSupplierDTO;
import static by.academy.util.TestConstants.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class SupplierServiceImplTest {

    private final SupplierService supplierService;
    private final SupplierRepository supplierRepository;

    @Autowired
    public SupplierServiceImplTest(SupplierService supplierService, SupplierRepository supplierRepository) {
        this.supplierService = supplierService;
        this.supplierRepository = supplierRepository;
    }

    @Test
    void createSupplier() {
        SupplierDTO result = supplierService.createSupplier(createSupplierDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(SUPPLIER_TEST_NAME, result.getName());
        assertEquals(CONTACT_PERSON, result.getContactPerson());
        assertEquals(TEST_ADDRESS_NAME, result.getAddressName());
        assertEquals(NUMBER, result.getPhoneNumberNumber());
    }

    @Test
    void readAllSuppliers() {
        List<SupplierDTO> result = supplierService.findAllSuppliers();
        assertNotNull(result);
    }

    @Test
    void updateSupplier() {
        SupplierDTO SupplierDTO = createSupplierDTO();
        SupplierDTO createdSupplierDTO = supplierService.createSupplier(SupplierDTO);

        SupplierDTO updatedSupplierDTO = supplierService.updateSupplier(1L, createdSupplierDTO)
                .orElseThrow();

        assertNotNull(updatedSupplierDTO);
        assertEquals(1L, updatedSupplierDTO.getId());
        assertEquals(createdSupplierDTO.getName(), updatedSupplierDTO.getName());
        assertEquals(createdSupplierDTO.getContactPerson(), updatedSupplierDTO.getContactPerson());
        assertEquals(createdSupplierDTO.getAddressName(), updatedSupplierDTO.getAddressName());
        assertEquals(createdSupplierDTO.getPhoneNumberNumber(), updatedSupplierDTO.getPhoneNumberNumber());
    }

    @Test
    void deleteSupplier() {
        SupplierDTO savedSupplier = supplierService.createSupplier(createSupplierDTO());

        supplierService.deleteSupplier(savedSupplier.getId());

        assertFalse(supplierRepository.existsById(savedSupplier.getId()));
    }

    @Test
    void selectSupplierById() {
        SupplierDTO savedSupplier = supplierService.createSupplier(createSupplierDTO());
        Optional<SupplierDTO> result = supplierService.findSupplierById(savedSupplier.getId());

        assertTrue(result.isPresent());

        SupplierDTO SupplierDTO = result.get();
        assertEquals(savedSupplier.getId(), SupplierDTO.getId());
        assertEquals(savedSupplier.getName(), SupplierDTO.getName());
        assertEquals(savedSupplier.getContactPerson(), SupplierDTO.getContactPerson());
        assertEquals(savedSupplier.getAddressName(), SupplierDTO.getAddressName());
        assertEquals(savedSupplier.getPhoneNumberNumber(), SupplierDTO.getPhoneNumberNumber());
    }

    @Test
    void findSupplierById() {
        SupplierDTO supplierDTO = supplierService.createSupplier(createSupplierDTO());

        SupplierDTO foundSupplierDTO = supplierService.findSupplierById(supplierDTO.getId())
                .orElseThrow();

        assertNotNull(foundSupplierDTO);
        assertEquals(SUPPLIER_TEST_NAME, foundSupplierDTO.getName());
        assertEquals(CONTACT_PERSON, foundSupplierDTO.getContactPerson());
        assertEquals(TEST_ADDRESS_NAME, foundSupplierDTO.getAddressName());
        assertEquals(NUMBER, foundSupplierDTO.getPhoneNumberNumber());
    }
}