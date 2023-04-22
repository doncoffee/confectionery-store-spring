package by.academy.service;

import by.academy.service.dto.BrandDTO;

import java.util.List;
import java.util.Optional;

public interface BrandService {

    BrandDTO createBrand(BrandDTO brandDTO);

    List<BrandDTO> findAllBrands();

    Optional<BrandDTO> updateBrand(Long id, BrandDTO brandDTO);

    boolean deleteBrand(Long id);

    Optional<BrandDTO> findBrandById(Long id);
}
