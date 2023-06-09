package by.academy.service;

import by.academy.service.dto.BrandDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    BrandDTO createBrand(BrandDTO brandDTO);

    Page<BrandDTO> findAllBrands(String search, Pageable pageable);
    List<BrandDTO> findAllBrands();

    Optional<BrandDTO> updateBrand(Long id, BrandDTO brandDTO);

    boolean deleteBrand(Long id);

    Optional<BrandDTO> findBrandById(Long id);
}
