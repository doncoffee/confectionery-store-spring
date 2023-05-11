package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.CookieService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.dto.CookieDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

import static by.academy.util.Constants.*;

@Controller
@RequestMapping(API_COOKIES)
@RequiredArgsConstructor
public class CookieController {
    private final CookieService cookieService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<CookieDTO> cookieDTOPage =
                cookieService.findAllCookies(search, pageable);
        model.addAttribute(COOKIES, cookieDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return COOKIE_COOKIES;
    }

    @PostMapping(ADD_COOKIE)
    public String create(@ModelAttribute @Validated CookieDTO cookieDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(BRANDS, brandService.findAllBrands());
            model.addAttribute(STORES, storeService.findAllStores());
            model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return COOKIE_ADD_COOKIE;
        } else {
            cookieService.createCookie(cookieDTO);
            return REDIRECT_API_COOKIES_PAGE + page + SIZE1 + size + SEARCH1 + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated CookieDTO cookieDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(COOKIE, cookieDTO);
            model.addAttribute(BRANDS, brandService.findAllBrands());
            model.addAttribute(STORES, storeService.findAllStores());
            model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return COOKIE_EDIT_COOKIE;
        } else {
            cookieService.updateCookie(id, cookieDTO)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
            return REDIRECT_API_COOKIES_PAGE + page + SIZE1 + size + SEARCH1 + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!cookieService.deleteCookie(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_COOKIE_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return cookieService.findCookieById(id)
                .map(cookieDTO -> {
                    model.addAttribute(COOKIE, cookieDTO);
                    model.addAttribute(BRANDS, brandService.findAllBrands());
                    model.addAttribute(STORES, storeService.findAllStores());
                    model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return COOKIE_EDIT_COOKIE;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_COOKIE)
    public String goToAddPage(Model model,
                              @RequestParam(PAGE) Integer page,
                              @RequestParam(SIZE) Integer size,
                              @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        model.addAttribute(BRANDS, brandService.findAllBrands());
        model.addAttribute(STORES, storeService.findAllStores());
        model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
        model.addAttribute(PAGE, page);
        model.addAttribute(SIZE, size);
        model.addAttribute(SEARCH, search);
        return COOKIE_ADD_COOKIE;
    }
}
