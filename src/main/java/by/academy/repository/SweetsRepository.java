package by.academy.repository;

import by.academy.entity.Sweets;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SweetsRepository extends JpaRepository<Sweets, Long> {

    @Query("SELECT s FROM Sweets s WHERE " +
            "CAST(s.price AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.type) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(s.weight AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.brand.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.composition) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.store.address.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.supplier.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Sweets> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
}
