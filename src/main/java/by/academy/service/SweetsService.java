package by.academy.service;

import by.academy.service.dto.SweetsDTO;

import java.util.List;
import java.util.Optional;

public interface SweetsService {

    SweetsDTO createSweets(SweetsDTO sweetsDTO);

    List<SweetsDTO> findAllSweets();

    Optional<SweetsDTO> updateSweets(Long id, SweetsDTO sweetsDTO);

    boolean deleteSweets(Long id);

    Optional<SweetsDTO> findSweetsById(Long id);
}
