package by.academy.service.impl;

import by.academy.mapper.impl.StoreMapper;
import by.academy.repository.StoreRepository;
import by.academy.service.StoreService;
import by.academy.service.dto.StoreDTO;
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
public class StoreServiceImpl implements StoreService {

    private final StoreRepository storeRepository;
    private final StoreMapper storeMapper;

    @Override
    public StoreDTO createStore(StoreDTO storeDTO) {
        return Optional.of(storeDTO)
                .map(storeMapper::mapToEntity)
                .map(storeRepository::save)
                .map(storeMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<StoreDTO> findAllStores(String search, Pageable pageable) {
        return (search == null) ?
                storeRepository.findAllByOrderById(pageable)
                        .map(storeMapper::mapToDTO) :
                storeRepository.findAllBySearchAndPage(search, pageable)
                        .map(storeMapper::mapToDTO);
    }

    @Override
    public List<StoreDTO> findAllStores() {
        return storeRepository.findAll().stream()
                .map(storeMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<StoreDTO> updateStore(Long id, StoreDTO storeDTO) {
        return storeRepository.findById(id)
                .map(entity -> storeMapper.map(storeDTO, entity))
                .map(storeRepository::save)
                .map(storeMapper::mapToDTO);
    }

    @Override
    public boolean deleteStore(Long id) {
        return storeRepository.findById(id)
                .map(entity -> {
                    storeRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<StoreDTO> findStoreById(Long id) {
        return storeRepository.findById(id)
                .map(storeMapper::mapToDTO);
    }
}
