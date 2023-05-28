package by.academy.service.impl;

import by.academy.repository.AddressRepository;
import by.academy.service.AddressService;
import by.academy.service.dto.AddressDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createAddressDTO;
import static by.academy.util.TestConstants.TEST_ADDRESS_NAME;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AddressServiceImplTest {

    private final AddressService addressService;
    private final AddressRepository addressRepository;

    @Autowired
    public AddressServiceImplTest(AddressService addressService, AddressRepository addressRepository) {
        this.addressService = addressService;
        this.addressRepository = addressRepository;
    }

    @Test
    void testCreateAddress() {
        AddressDTO result = addressService.createAddress(createAddressDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(TEST_ADDRESS_NAME, result.getName());
    }

    @Test
    void testReadAllAddresses() {
        List<AddressDTO> resultBeforeAddingNewAddresses =
                addressService.findAllAddresses();

        addressService.createAddress(createAddressDTO());
        addressService.createAddress(createAddressDTO());

        List<AddressDTO> resultAfterAddingNewAddresses =
                addressService.findAllAddresses();

        assertNotNull(resultBeforeAddingNewAddresses);
        assertNotNull(resultAfterAddingNewAddresses);
        assertEquals(TEST_ADDRESS_NAME, resultAfterAddingNewAddresses.get(
                        resultAfterAddingNewAddresses.size() - 1).getName());
        assertEquals(resultBeforeAddingNewAddresses.size() + 2,
                resultAfterAddingNewAddresses.size());
    }

    @Test
    void testUpdateAddress() {
        AddressDTO addressDTO = createAddressDTO();
        AddressDTO createdAddressDTO = addressService.createAddress(addressDTO);

        AddressDTO updatedAddressDTO = addressService.updateAddress(1L, createdAddressDTO)
                .orElseThrow();

        assertNotNull(updatedAddressDTO);
        assertEquals(1L, updatedAddressDTO.getId());
        assertEquals(createdAddressDTO.getName(), updatedAddressDTO.getName());
    }

    @Test
    void testDeleteAddress() {
        AddressDTO savedAddress = addressService.createAddress(createAddressDTO());

        addressService.deleteAddress(savedAddress.getId());

        assertFalse(addressRepository.existsById(savedAddress.getId()));
    }

    @Test
    void testSelectAddressById() {
        AddressDTO savedAddress = addressService.createAddress(createAddressDTO());
        Optional<AddressDTO> result = addressService.findAddressById(savedAddress.getId());

        assertTrue(result.isPresent());

        AddressDTO addressDTO = result.get();
        assertEquals(savedAddress.getId(), addressDTO.getId());
        assertEquals(savedAddress.getName(), addressDTO.getName());
    }

    @Test
    void findAddressById() {
        AddressDTO addressDTO = addressService.createAddress(createAddressDTO());

        AddressDTO foundAddressDTO = addressService.findAddressById(addressDTO.getId())
                .orElseThrow();

        assertNotNull(foundAddressDTO);
        assertEquals(TEST_ADDRESS_NAME, foundAddressDTO.getName());
    }
}