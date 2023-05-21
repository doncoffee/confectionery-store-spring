package by.academy.service.impl;

import by.academy.mapper.impl.BrandMapper;
import by.academy.repository.BrandRepository;
import by.academy.service.BrandService;
import by.academy.service.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    @Override
    public BrandDTO createBrand(BrandDTO brandDTO) {
        return Optional.of(brandDTO)
                .map(brandMapper::mapToEntity)
                .map(brandRepository::save)
                .map(brandMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<BrandDTO> findAllBrands(String search, Pageable pageable) {
        return (search == null) ?
                brandRepository.findAllByOrderById(pageable)
                        .map(brandMapper::mapToDTO) :
                brandRepository.findAllBySearchAndPage(search, pageable)
                        .map(brandMapper::mapToDTO);
    }

    @Override
    public List<BrandDTO> findAllBrands() {
        return brandRepository.findAll().stream()
                .map(brandMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<BrandDTO> updateBrand(Long id, BrandDTO brandDTO) {
        return brandRepository.findById(id)
                .map(entity -> {
                    BeanUtils.copyProperties(brandDTO, entity);
                    return brandRepository.save(entity);
                })
                .map(brandMapper::mapToDTO);
    }

    @Override
    public boolean deleteBrand(Long id) {
        return brandRepository.findById(id)
                .map(entity -> {
                    brandRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<BrandDTO> findBrandById(Long id) {
        return brandRepository.findById(id)
                .map(brandMapper::mapToDTO);
    }
}
