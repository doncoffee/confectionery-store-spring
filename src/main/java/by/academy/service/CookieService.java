package by.academy.service;

import by.academy.service.dto.CookieDTO;

import java.util.List;
import java.util.Optional;

public interface CookieService {

    CookieDTO createCookie(CookieDTO cookieDTO);

    List<CookieDTO> findAllCookies();

    Optional<CookieDTO> updateCookie(Long id, CookieDTO cookieDTO);

    boolean deleteCookie(Long id);

    Optional<CookieDTO> findCookieById(Long id);
}
