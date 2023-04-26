package by.academy.repository;

import by.academy.entity.Store;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StoreRepository extends JpaRepository<Store, Long> {

    @Query("SELECT s FROM Store s WHERE " +
            "LOWER(s.address.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "LOWER(s.phoneNumber.number) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Store> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
}
