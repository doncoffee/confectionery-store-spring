package by.academy.service.impl;

import by.academy.mapper.impl.CookieMapper;
import by.academy.repository.CookieRepository;
import by.academy.service.CookieService;
import by.academy.service.dto.CookieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class CookieServiceImpl implements CookieService {

    private final CookieRepository cookieRepository;
    private final CookieMapper cookieMapper;

    @Override
    public CookieDTO createCookie(CookieDTO cookieDTO) {
        return Optional.of(cookieDTO)
                .map(cookieMapper::mapToEntity)
                .map(cookieRepository::save)
                .map(cookieMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<CookieDTO> findAllCookies(String search, Pageable pageable) {
        return (search == null) ?
                cookieRepository.findAll(pageable)
                        .map(cookieMapper::mapToDTO) :
                cookieRepository.findAllBySearchAndPage(search, pageable)
                        .map(cookieMapper::mapToDTO);
    }

    @Override
    public List<CookieDTO> findAllCookies() {
        return cookieRepository.findAll().stream()
                .map(cookieMapper::mapToDTO)
                .toList();
    }

    @Override
    public Optional<CookieDTO> updateCookie(Long id, CookieDTO cookieDTO) {
        return cookieRepository.findById(id)
                .map(entity -> cookieMapper.map(cookieDTO, entity))
                .map(cookieRepository::save)
                .map(cookieMapper::mapToDTO);
    }

    @Override
    public boolean deleteCookie(Long id) {
        return cookieRepository.findById(id)
                .map(entity -> {
                    cookieRepository.delete(entity);
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Optional<CookieDTO> findCookieById(Long id) {
        return cookieRepository.findById(id)
                .map(cookieMapper::mapToDTO);
    }
}
