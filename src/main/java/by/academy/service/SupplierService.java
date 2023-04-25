package by.academy.service;

import by.academy.service.dto.SupplierDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SupplierService {

    SupplierDTO createSupplier(SupplierDTO supplierDTO);

    Page<SupplierDTO> findAllSuppliers(Pageable pageable);
    List<SupplierDTO> findAllSuppliers();

    Optional<SupplierDTO> updateSupplier(Long id, SupplierDTO supplierDTO);

    boolean deleteSupplier(Long id);

    Optional<SupplierDTO> findSupplierById(Long id);
}
