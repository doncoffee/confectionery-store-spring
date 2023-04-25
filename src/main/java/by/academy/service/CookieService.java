package by.academy.service;

import by.academy.service.dto.CookieDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface CookieService {

    CookieDTO createCookie(CookieDTO cookieDTO);

    Page<CookieDTO> findAllCookies(Pageable pageable);
    List<CookieDTO> findAllCookies();

    Optional<CookieDTO> updateCookie(Long id, CookieDTO cookieDTO);

    boolean deleteCookie(Long id);

    Optional<CookieDTO> findCookieById(Long id);
}
