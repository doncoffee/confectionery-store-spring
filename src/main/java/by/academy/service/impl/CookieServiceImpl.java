package by.academy.service.impl;

import by.academy.entity.Brand;
import by.academy.entity.Cookie;
import by.academy.entity.Store;
import by.academy.entity.Supplier;
import by.academy.mapper.impl.CookieMapper;
import by.academy.repository.BrandRepository;
import by.academy.repository.CookieRepository;
import by.academy.repository.StoreRepository;
import by.academy.repository.SupplierRepository;
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
    private final BrandRepository brandRepository;
    private final StoreRepository storeRepository;
    private final SupplierRepository supplierRepository;

    @Override
    public CookieDTO createCookie(CookieDTO cookieDTO) {
        return Optional.of(cookieDTO)
                .map(dto -> {
                    Cookie cookie = new Cookie();
                    copy(dto, cookie);
                    return cookie;
                })
                .map(cookieRepository::save)
                .map(cookieMapper::mapToDTO)
                .orElseThrow();
    }

    @Override
    public Page<CookieDTO> findAllCookies(String search, Pageable pageable) {
        return (search == null) ?
                cookieRepository.findAllByOrderById(pageable)
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
                .map(entity -> copy(cookieDTO, entity))
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

    private Brand getBrand(Long brandId) {
        return Optional.ofNullable(brandId)
                .flatMap(brandRepository::findById)
                .orElse(null);
    }

    private Store getStore(Long storeId) {
        return Optional.ofNullable(storeId)
                .flatMap(storeRepository::findById)
                .orElse(null);
    }

    private Supplier getSupplier(Long supplierId) {
        return Optional.ofNullable(supplierId)
                .flatMap(supplierRepository::findById)
                .orElse(null);
    }

    private Cookie copy(CookieDTO cookieDTO, Cookie cookie) {
        cookie.setId(cookieDTO.getId());
        cookie.setPrice(cookieDTO.getPrice());
        cookie.setType(cookieDTO.getType());
        cookie.setWeight(cookieDTO.getWeight());
        cookie.setComposition(cookieDTO.getComposition());
        cookie.setBrand(getBrand(cookieDTO.getBrandId()));
        cookie.setStore(getStore(cookieDTO.getStoreId()));
        cookie.setSupplier(getSupplier(cookieDTO.getSupplierId()));
        return cookie;
    }
}
