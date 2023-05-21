package by.academy.service.impl;

import by.academy.entity.Brand;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.entity.Sweets;
import by.academy.mapper.impl.SweetsMapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
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
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public SweetsDTO createSweets(SweetsDTO sweetsDTO) {
        return Optional.of(sweetsDTO)
                .map(dto -> {
                    Sweets sweets = new Sweets();
                    copy(dto, sweets);
                    return sweets;
                })
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
                .map(entity -> copy(sweetsDTO, entity))
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

    private Brand getBrand(Long brandId) {
        return Optional.ofNullable(brandId)
                .flatMap(brandRepository::findById)
                .orElse(null);
    }

    private Store getStore(Long storeId) {
        return Optional.ofNullable(storeId)
                .flatMap(storeRepository::findById)
                .orElse(null);
    }

    private Supplier getSupplier(Long supplierId) {
        return Optional.ofNullable(supplierId)
                .flatMap(supplierRepository::findById)
                .orElse(null);
    }

    private Sweets copy(SweetsDTO sweetsDTO, Sweets sweets) {
        sweets.setId(sweetsDTO.getId());
        sweets.setPrice(sweetsDTO.getPrice());
        sweets.setType(sweetsDTO.getType());
        sweets.setWeight(sweetsDTO.getWeight());
        sweets.setComposition(sweetsDTO.getComposition());
        sweets.setBrand(getBrand(sweetsDTO.getBrandId()));
        sweets.setStore(getStore(sweetsDTO.getStoreId()));
        sweets.setSupplier(getSupplier(sweetsDTO.getSupplierId()));
        return sweets;
    }
}
