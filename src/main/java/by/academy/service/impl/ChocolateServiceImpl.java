package by.academy.service.impl;

import by.academy.mapper.impl.ChocolateMapper;
import by.academy.repository.ChocolateRepository;
import by.academy.service.ChocolateService;
import by.academy.service.dto.ChocolateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ChocolateServiceImpl implements ChocolateService {

    private final ChocolateRepository chocolateRepository;
    private final ChocolateMapper chocolateMapper;

    @Override
    public ChocolateDTO createChocolate(ChocolateDTO chocolateDTO) {
        return Optional.of(chocolateDTO)
                .map(chocolateMapper::mapToEntity)
                .map(chocolateRepository::save)
                .map(chocolateMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public List<ChocolateDTO> findAllChocolates() {
        return chocolateRepository.findAll().stream()
                .map(chocolateMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<ChocolateDTO> updateChocolate(Long id, ChocolateDTO chocolateDTO) {
        return chocolateRepository.findById(id)
                .map(entity -> chocolateMapper.map(chocolateDTO, entity))
                .map(chocolateRepository::save)
                .map(chocolateMapper::mapToDTO);
    }

    @Override
    public boolean deleteChocolate(Long id) {
        return chocolateRepository.findById(id)
                .map(entity -> {
                    chocolateRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<ChocolateDTO> findChocolateById(Long id) {
        return chocolateRepository.findById(id)
                .map(chocolateMapper::mapToDTO);
    }
}
