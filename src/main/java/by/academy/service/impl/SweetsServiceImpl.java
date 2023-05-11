package by.academy.service.impl;

import by.academy.mapper.impl.SweetsMapper;
import by.academy.repository.SweetsRepository;
import by.academy.service.SweetsService;
import by.academy.service.dto.SweetsDTO;
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
public class SweetsServiceImpl implements SweetsService {

    private final SweetsRepository sweetsRepository;
    private final SweetsMapper sweetsMapper;

    @Override
    public SweetsDTO createSweets(SweetsDTO sweetsDTO) {
        return Optional.of(sweetsDTO)
                .map(sweetsMapper::mapToEntity)
                .map(sweetsRepository::save)
                .map(sweetsMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<SweetsDTO> findAllSweets(String search, Pageable pageable) {
        return (search == null) ?
                sweetsRepository.findAllByOrderById(pageable)
                        .map(sweetsMapper::mapToDTO) :
                sweetsRepository.findAllBySearchAndPage(search, pageable)
                        .map(sweetsMapper::mapToDTO);
    }

    @Override
    public List<SweetsDTO> findAllSweets() {
        return sweetsRepository.findAll().stream()
                .map(sweetsMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<SweetsDTO> updateSweets(Long id, SweetsDTO sweetsDTO) {
        return sweetsRepository.findById(id)
                .map(entity -> sweetsMapper.map(sweetsDTO, entity))
                .map(sweetsRepository::save)
                .map(sweetsMapper::mapToDTO);
    }

    @Override
    public boolean deleteSweets(Long id) {
        return sweetsRepository.findById(id)
                .map(entity -> {
                    sweetsRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<SweetsDTO> findSweetsById(Long id) {
        return sweetsRepository.findById(id)
                .map(sweetsMapper::mapToDTO);
    }
}
