package by.academy.service;

import by.academy.service.dto.SweetsDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SweetsService {

    SweetsDTO createSweets(SweetsDTO sweetsDTO);

    Page<SweetsDTO> findAllSweets(String search, Pageable pageable);
    List<SweetsDTO> findAllSweets();

    Optional<SweetsDTO> updateSweets(Long id, SweetsDTO sweetsDTO);

    boolean deleteSweets(Long id);

    Optional<SweetsDTO> findSweetsById(Long id);
}
