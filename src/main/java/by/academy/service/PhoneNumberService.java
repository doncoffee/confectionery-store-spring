package by.academy.service;

import by.academy.service.dto.PhoneNumberDTO;

import java.util.List;
import java.util.Optional;

public interface PhoneNumberService {

    PhoneNumberDTO createPhoneNumber(PhoneNumberDTO phoneNumberDTO);

    List<PhoneNumberDTO> findAllPhoneNumbers();

    Optional<PhoneNumberDTO> updatePhoneNumber(Long id, PhoneNumberDTO phoneNumberDTO);

    boolean deletePhoneNumber(Long id);

    Optional<PhoneNumberDTO> findPhoneNumberById(Long id);
}
