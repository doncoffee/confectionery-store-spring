package by.academy.service;

import by.academy.service.dto.SupplierDTO;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO);

    List<SupplierDTO> findAllSuppliers();

    Optional<SupplierDTO> updateSupplier(Long id, SupplierDTO supplierDTO);

    boolean deleteSupplier(Long id);

    Optional<SupplierDTO> findSupplierById(Long id);
}
