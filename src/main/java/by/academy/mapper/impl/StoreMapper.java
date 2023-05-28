package by.academy.mapper.impl;

import by.academy.entity.Store;
import by.academy.mapper.Mapper;
import by.academy.service.dto.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.academy.util.Constants.USE_COPY_METHOD_IN_STORE_SERVICE_IMPL_INSTEAD;

@Component
@RequiredArgsConstructor
public class StoreMapper implements Mapper<Store, StoreDTO> {

    @Override
    public Store mapToEntity(StoreDTO object) {
        throw new UnsupportedOperationException(USE_COPY_METHOD_IN_STORE_SERVICE_IMPL_INSTEAD);
    }

    @Override
    public StoreDTO mapToDTO(Store object) {
        return StoreDTO.builder()
                .id(object.getId())
                .addressId(object.getAddress().getId())
                .addressName(object.getAddress().getName())
                .phoneNumberId(object.getPhoneNumber().getId())
                .phoneNumberNumber(object.getPhoneNumber().getNumber())
                .build();
    }
}
