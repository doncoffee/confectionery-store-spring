package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.SweetsService;
import by.academy.service.dto.SweetsDTO;
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
@RequestMapping(API_SWEETS)
@RequiredArgsConstructor
public class SweetsController {
    private final SweetsService sweetsService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<SweetsDTO> sweetsDTOPage =
                sweetsService.findAllSweets(search, pageable);
        model.addAttribute(SWEETS, sweetsDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return SWEETS_SWEETS;
    }

    @PostMapping(ADD_SWEETS)
    public String create(@ModelAttribute @Validated SweetsDTO sweetsDTO,
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
            return SWEETS_ADD_SWEETS;
        } else {
            sweetsService.createSweets(sweetsDTO);
            return REDIRECT_API_SWEETS_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated SweetsDTO sweetsDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(SWEETS, sweetsDTO);
            model.addAttribute(BRANDS, brandService.findAllBrands());
            model.addAttribute(STORES, storeService.findAllStores());
            model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return SWEETS_EDIT_SWEETS;
        } else {
            sweetsService.updateSweets(id, sweetsDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return REDIRECT_API_SWEETS_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!sweetsService.deleteSweets(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_SWEETS_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return sweetsService.findSweetsById(id)
                .map(sweetsDTO -> {
                    model.addAttribute(SWEETS, sweetsDTO);
                    model.addAttribute(BRANDS, brandService.findAllBrands());
                    model.addAttribute(STORES, storeService.findAllStores());
                    model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return SWEETS_EDIT_SWEETS;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_SWEETS)
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
        return SWEETS_ADD_SWEETS;
    }
}
