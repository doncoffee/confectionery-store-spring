package by.academy.mapper.impl;

import by.academy.entity.Brand;
import by.academy.entity.Cookie;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.mapper.Mapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
import by.academy.service.dto.CookieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class CookieMapper implements Mapper<Cookie, CookieDTO> {
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Cookie mapToEntity(CookieDTO object) {
        return Cookie.builder()
                .id(object.getId())
                .price(object.getPrice())
                .type(object.getType())
                .weight(object.getWeight())
                .composition(object.getComposition())
                .brand(getBrand(object.getBrandId()))
                .store(getStore(object.getStoreId()))
                .supplier(getSupplier(object.getSupplierId()))
                .build();
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

    @Override
    public Cookie map(CookieDTO fromObject, Cookie toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(CookieDTO cookieDTO, Cookie cookie) {
        cookie.setPrice(cookieDTO.getPrice());
        cookie.setType(cookieDTO.getType());
        cookie.setWeight(cookieDTO.getWeight());
        cookie.setComposition(cookieDTO.getComposition());
        cookie.setBrand(getBrand(cookieDTO.getBrandId()));
        cookie.setStore(getStore(cookieDTO.getStoreId()));
        cookie.setSupplier(getSupplier(cookieDTO.getSupplierId()));
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
}
