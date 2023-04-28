package by.academy.repository;

import by.academy.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SupplierRepository extends JpaRepository<Supplier, Long> {

    @Query("SELECT s FROM Supplier s WHERE " +
            "LOWER(s.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.contactPerson) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.address.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.phoneNumber.number) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "GROUP BY s.id ORDER BY s.id")
    Page<Supplier> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
    Page<Supplier> findAllByOrderById(Pageable pageable);
}
