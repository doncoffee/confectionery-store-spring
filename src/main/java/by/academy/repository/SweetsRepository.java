package by.academy.repository;

import by.academy.entity.Sweets;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SweetsRepository extends JpaRepository<Sweets, Long> {
}
