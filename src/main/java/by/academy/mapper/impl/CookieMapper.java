package by.academy.mapper.impl;

import by.academy.entity.Cookie;
import by.academy.mapper.Mapper;
import by.academy.service.dto.CookieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.academy.util.Constants.USE_COPY_METHOD_IN_COOKIE_SERVICE_IMPL_INSTEAD;

@Component
@RequiredArgsConstructor
public class CookieMapper implements Mapper<Cookie, CookieDTO> {

    @Override
    public Cookie mapToEntity(CookieDTO object) {
        throw new UnsupportedOperationException(USE_COPY_METHOD_IN_COOKIE_SERVICE_IMPL_INSTEAD);
    }

    @Override
    public CookieDTO mapToDTO(Cookie object) {
        return CookieDTO.builder()
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
