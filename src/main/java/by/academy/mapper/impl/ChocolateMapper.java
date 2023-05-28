package by.academy.mapper.impl;

import by.academy.entity.Chocolate;
import by.academy.mapper.Mapper;
import by.academy.service.dto.ChocolateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.academy.util.Constants.USE_COPY_METHOD_IN_CHOCOLATE_SERVICE_IMPL_INSTEAD;

@Component
@RequiredArgsConstructor
public class ChocolateMapper implements Mapper<Chocolate, ChocolateDTO> {

    @Override
    public Chocolate mapToEntity(ChocolateDTO object) {
        throw new UnsupportedOperationException(USE_COPY_METHOD_IN_CHOCOLATE_SERVICE_IMPL_INSTEAD);
    }

    @Override
    public ChocolateDTO mapToDTO(Chocolate object) {
        return ChocolateDTO.builder()
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
