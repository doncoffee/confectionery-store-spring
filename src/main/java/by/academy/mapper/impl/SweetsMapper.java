package by.academy.mapper.impl;

import by.academy.entity.Brand;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.entity.Sweets;
import by.academy.mapper.Mapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
import by.academy.service.dto.SweetsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SweetsMapper implements Mapper<Sweets, SweetsDTO> {
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Sweets mapToEntity(SweetsDTO object) {
        return Sweets.builder()
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

    @Override
    public Sweets map(SweetsDTO fromObject, Sweets toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(SweetsDTO sweetsDTO, Sweets sweets) {
        sweets.setPrice(sweetsDTO.getPrice());
        sweets.setType(sweetsDTO.getType());
        sweets.setWeight(sweetsDTO.getWeight());
        sweets.setComposition(sweetsDTO.getComposition());
        sweets.setBrand(getBrand(sweetsDTO.getBrandId()));
        sweets.setStore(getStore(sweetsDTO.getStoreId()));
        sweets.setSupplier(getSupplier(sweetsDTO.getSupplierId()));
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
