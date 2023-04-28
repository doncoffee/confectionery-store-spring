package by.academy.repository;

import by.academy.entity.Brand;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    @Query("SELECT b FROM Brand b WHERE " +
            "LOWER(b.name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "GROUP BY b.id ORDER BY b.id")
    Page<Brand> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
    Page<Brand> findAllByOrderById(Pageable pageable);
}
