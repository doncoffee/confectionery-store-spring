package by.academy.repository;

import by.academy.entity.Chocolate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChocolateRepository extends JpaRepository<Chocolate, Long> {
}
