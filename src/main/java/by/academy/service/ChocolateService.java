package by.academy.service;

import by.academy.service.dto.ChocolateDTO;

import java.util.List;
import java.util.Optional;

public interface ChocolateService {

    ChocolateDTO createChocolate(ChocolateDTO chocolateDTO);

    List<ChocolateDTO> findAllChocolates();

    Optional<ChocolateDTO> updateChocolate(Long id, ChocolateDTO chocolateDTO);

    boolean deleteChocolate(Long id);

    Optional<ChocolateDTO> findChocolateById(Long id);
}
