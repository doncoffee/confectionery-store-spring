package by.academy.repository;

import by.academy.entity.PhoneNumber;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    @Query("SELECT p FROM PhoneNumber p WHERE " +
            "LOWER(p.number) LIKE LOWER(CONCAT('%', :search, '%'))")
    Page<PhoneNumber> findAllBySearchAndPage(@Param("search") String search, Pageable pageable);
}
