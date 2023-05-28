package by.academy.mapper.impl;

import by.academy.entity.Supplier;
import by.academy.mapper.Mapper;
import by.academy.service.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static by.academy.util.Constants.USE_COPY_METHOD_IN_SUPPLIER_SERVICE_IMPL_INSTEAD;

@Component
@RequiredArgsConstructor
public class SupplierMapper implements Mapper<Supplier, SupplierDTO> {

    @Override
    public Supplier mapToEntity(SupplierDTO object) {
        throw new UnsupportedOperationException(USE_COPY_METHOD_IN_SUPPLIER_SERVICE_IMPL_INSTEAD);
    }

    @Override
    public SupplierDTO mapToDTO(Supplier object) {
        return SupplierDTO.builder()
                .id(object.getId())
                .name(object.getName())
                .contactPerson(object.getContactPerson())
                .addressId(object.getAddress().getId())
                .addressName(object.getAddress().getName())
                .phoneNumberId(object.getPhoneNumber().getId())
                .phoneNumberNumber(object.getPhoneNumber().getNumber())
                .build();
    }
}
