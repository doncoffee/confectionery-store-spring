package by.academy.repository;

import by.academy.entity.Cookie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CookieRepository extends JpaRepository<Cookie, Long> {

    @Query("SELECT c FROM Cookie c WHERE " +
            "CAST(c.price AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.type) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(c.weight AS string) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.brand.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.composition) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.store.address.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(c.supplier.name) LIKE LOWER(CONCAT('%', :search, '%'))" +
            "GROUP BY c.id ORDER BY c.id")
    Page<Cookie> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
    Page<Cookie> findAllByOrderById(Pageable pageable);
}
