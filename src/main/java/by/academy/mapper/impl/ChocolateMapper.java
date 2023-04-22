package by.academy.mapper.impl;

import by.academy.entity.Brand;
import by.academy.entity.Chocolate;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.mapper.Mapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
import by.academy.service.dto.ChocolateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ChocolateMapper implements Mapper<Chocolate, ChocolateDTO> {
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public Chocolate mapToEntity(ChocolateDTO object) {
        return Chocolate.builder()
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

    @Override
    public Chocolate map(ChocolateDTO fromObject, Chocolate toObject) {
        copy(fromObject, toObject);
        return toObject;
    }

    private void copy(ChocolateDTO chocolateDTO, Chocolate chocolate) {
        chocolate.setPrice(chocolateDTO.getPrice());
        chocolate.setType(chocolateDTO.getType());
        chocolate.setWeight(chocolateDTO.getWeight());
        chocolate.setComposition(chocolateDTO.getComposition());
        chocolate.setBrand(getBrand(chocolateDTO.getBrandId()));
        chocolate.setStore(getStore(chocolateDTO.getStoreId()));
        chocolate.setSupplier(getSupplier(chocolateDTO.getSupplierId()));
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
