package by.academy.repository;

import by.academy.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AddressRepository extends JpaRepository<Address, Long> {

    @Query("SELECT a FROM Address a WHERE " +
            "LOWER(a.name) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<Address> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
}
