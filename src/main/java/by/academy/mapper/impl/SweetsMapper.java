package by.academy.mapper.impl;

import by.academy.entity.Sweets;
import by.academy.mapper.Mapper;
import by.academy.service.dto.SweetsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.academy.util.Constants.USE_COPY_METHOD_IN_SWEETS_SERVICE_IMPL_INSTEAD;

@Component
@RequiredArgsConstructor
public class SweetsMapper implements Mapper<Sweets, SweetsDTO> {

    @Override
    public Sweets mapToEntity(SweetsDTO object) {
        throw new UnsupportedOperationException(USE_COPY_METHOD_IN_SWEETS_SERVICE_IMPL_INSTEAD);
    }

    @Override
    public SweetsDTO mapToDTO(Sweets object) {
        return SweetsDTO.builder()
                .id(object.getId())
                .price(object.getPrice())
                .type(object.getType())
                .weight(object.getWeight())
                .composition(object.getComposition())
                .brandId(object.getBrand().getId())
                .brandName(object.getBrand().getName())
                .storeId(object.getStore().getId())
                .storeName(object.getStore().getAddress().getName())
                .supplierId(object.getSupplier().getId())
                .supplierName(object.getSupplier().getName())
                .build();
    }
}
