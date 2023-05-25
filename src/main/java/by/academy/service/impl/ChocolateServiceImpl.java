package by.academy.service.impl;

import by.academy.entity.Brand;
import by.academy.entity.Chocolate;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.mapper.impl.ChocolateMapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.ChocolateRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
import by.academy.service.ChocolateService;
import by.academy.service.dto.ChocolateDTO;
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
public class ChocolateServiceImpl implements ChocolateService {

    private final ChocolateRepository chocolateRepository;
    private final ChocolateMapper chocolateMapper;
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public ChocolateDTO createChocolate(ChocolateDTO chocolateDTO) {
        return Optional.of(chocolateDTO)
                .map(dto -> {
                    Chocolate chocolate = new Chocolate();
                    copy(dto, chocolate);
                    return chocolate;
                })
                .map(chocolateRepository::save)
                .map(chocolateMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<ChocolateDTO> findAllChocolates(String search, Pageable pageable) {
        return (search == null) ?
                chocolateRepository.findAllByOrderById(pageable)
                        .map(chocolateMapper::mapToDTO) :
                chocolateRepository.findAllBySearchAndPage(search, pageable)
                        .map(chocolateMapper::mapToDTO);
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
                .map(entity -> copy(chocolateDTO, entity))
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

    private Chocolate copy(ChocolateDTO chocolateDTO, Chocolate chocolate) {
        chocolate.setPrice(chocolateDTO.getPrice());
        chocolate.setType(chocolateDTO.getType());
        chocolate.setWeight(chocolateDTO.getWeight());
        chocolate.setComposition(chocolateDTO.getComposition());
        chocolate.setBrand(getBrand(chocolateDTO.getBrandId()));
        chocolate.setStore(getStore(chocolateDTO.getStoreId()));
        chocolate.setSupplier(getSupplier(chocolateDTO.getSupplierId()));
        return chocolate;
    }
}
