package by.academy.mapper.impl;

import by.academy.entity.Brand;
import by.academy.mapper.Mapper;
import by.academy.service.dto.BrandDTO;
import org.springframework.stereotype.Component;

@Component
public class BrandMapper implements Mapper<Brand, BrandDTO> {
    @Override
    public Brand mapToEntity(BrandDTO object) {
        return Brand.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public BrandDTO mapToDTO(Brand object) {
        return BrandDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .build();
    }

    @Override
    public Brand map(BrandDTO fromObject, Brand toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(BrandDTO brandDTO, Brand brand) {
        brand.setName(brandDTO.getName());
    }
}
