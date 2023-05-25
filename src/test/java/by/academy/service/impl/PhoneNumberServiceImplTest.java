package by.academy.service.impl;

import by.academy.repository.PhoneNumberRepository;
import by.academy.service.PhoneNumberService;
import by.academy.service.dto.PhoneNumberDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.MockUtil.createPhoneNumberDTO;
import static by.academy.util.TestConstants.NUMBER;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PhoneNumberServiceImplTest {

    private final PhoneNumberService phoneNumberService;
    private final PhoneNumberRepository phoneNumberRepository;

    @Autowired
    public PhoneNumberServiceImplTest(PhoneNumberService phoneNumberService, PhoneNumberRepository phoneNumberRepository) {
        this.phoneNumberService = phoneNumberService;
        this.phoneNumberRepository = phoneNumberRepository;
    }

    @Test
    void createPhoneNumber() {
        PhoneNumberDTO result = phoneNumberService.createPhoneNumber(createPhoneNumberDTO());
        assertNotNull(result);
        assertNotNull(result.getId()); // Assuming the ID is generated during creation
        assertEquals(NUMBER, result.getNumber());
    }

    @Test
    void readAllPhoneNumbers() {
        List<PhoneNumberDTO> resultBeforeAddingNewPhoneNumbers =
                phoneNumberService.findAllPhoneNumbers();

        phoneNumberService.createPhoneNumber(createPhoneNumberDTO());
        phoneNumberService.createPhoneNumber(createPhoneNumberDTO());

        List<PhoneNumberDTO> resultAfterAddingNewPhoneNumbers =
                phoneNumberService.findAllPhoneNumbers();

        assertNotNull(resultBeforeAddingNewPhoneNumbers);
        assertNotNull(resultAfterAddingNewPhoneNumbers);
        assertEquals(NUMBER,
                resultAfterAddingNewPhoneNumbers.get(
                        resultAfterAddingNewPhoneNumbers.size() - 1).getNumber());
        assertEquals(resultBeforeAddingNewPhoneNumbers.size() + 2,
                resultAfterAddingNewPhoneNumbers.size());
    }

    @Test
    void updatePhoneNumber() {
        PhoneNumberDTO PhoneNumberDTO = createPhoneNumberDTO();
        PhoneNumberDTO createdPhoneNumberDTO = phoneNumberService.createPhoneNumber(PhoneNumberDTO);

        PhoneNumberDTO updatedPhoneNumberDTO = phoneNumberService.updatePhoneNumber(1L, createdPhoneNumberDTO)
                .orElseThrow();

        assertNotNull(updatedPhoneNumberDTO);
        assertEquals(1L, updatedPhoneNumberDTO.getId());
        assertEquals(createdPhoneNumberDTO.getNumber(), updatedPhoneNumberDTO.getNumber());
    }

    @Test
    void deletePhoneNumber() {
        PhoneNumberDTO savedPhoneNumber = phoneNumberService.createPhoneNumber(createPhoneNumberDTO());

        phoneNumberService.deletePhoneNumber(savedPhoneNumber.getId());

        assertFalse(phoneNumberRepository.existsById(savedPhoneNumber.getId()));
    }

    @Test
    void selectPhoneNumberById() {
        PhoneNumberDTO savedPhoneNumber = phoneNumberService.createPhoneNumber(createPhoneNumberDTO());
        Optional<PhoneNumberDTO> result = phoneNumberService.findPhoneNumberById(savedPhoneNumber.getId());

        assertTrue(result.isPresent());

        PhoneNumberDTO PhoneNumberDTO = result.get();
        assertEquals(savedPhoneNumber.getId(), PhoneNumberDTO.getId());
        assertEquals(savedPhoneNumber.getNumber(), PhoneNumberDTO.getNumber());
    }

    @Test
    void findPhoneNumberById() {
        PhoneNumberDTO phoneNumberDTO = phoneNumberService.createPhoneNumber(createPhoneNumberDTO());

        PhoneNumberDTO foundPhoneNumberDTO = phoneNumberService.findPhoneNumberById(phoneNumberDTO.getId())
                .orElseThrow();

        assertNotNull(foundPhoneNumberDTO);
        assertEquals(NUMBER, foundPhoneNumberDTO.getNumber());
    }
}