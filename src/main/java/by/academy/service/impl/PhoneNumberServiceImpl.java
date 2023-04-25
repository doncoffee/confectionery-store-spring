package by.academy.service.impl;

import by.academy.mapper.impl.PhoneNumberMapper;
import by.academy.repository.PhoneNumberRepository;
import by.academy.service.PhoneNumberService;
import by.academy.service.dto.PhoneNumberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final PhoneNumberMapper phoneNumberMapper;

    @Override
    public PhoneNumberDTO createPhoneNumber(PhoneNumberDTO phoneNumberDTO) {
        return Optional.of(phoneNumberDTO)
                .map(phoneNumberMapper::mapToEntity)
                .map(phoneNumberRepository::save)
                .map(phoneNumberMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<PhoneNumberDTO> findAllPhoneNumbers(Pageable pageable) {
        return phoneNumberRepository.findAll(pageable)
                .map(phoneNumberMapper::mapToDTO);
    }

    @Override
    public List<PhoneNumberDTO> findAllPhoneNumbers() {
        return phoneNumberRepository.findAll().stream()
                .map(phoneNumberMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<PhoneNumberDTO> updatePhoneNumber(Long id,
                                                      PhoneNumberDTO phoneNumberDTO) {
        return phoneNumberRepository.findById(id)
                .map(entity -> phoneNumberMapper.map(phoneNumberDTO, entity))
                .map(phoneNumberRepository::save)
                .map(phoneNumberMapper::mapToDTO);
    }

    @Override
    public boolean deletePhoneNumber(Long id) {
        return phoneNumberRepository.findById(id)
                .map(entity -> {
                    phoneNumberRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<PhoneNumberDTO> findPhoneNumberById(Long id) {
        return phoneNumberRepository.findById(id)
                .map(phoneNumberMapper::mapToDTO);
    }
}
