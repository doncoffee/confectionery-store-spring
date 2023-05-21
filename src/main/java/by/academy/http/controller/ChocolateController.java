package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.ChocolateService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.dto.ChocolateDTO;
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
@RequestMapping(API_CHOCOLATES)
@RequiredArgsConstructor
public class ChocolateController {
    private final ChocolateService chocolateService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<ChocolateDTO> chocolateDTOPage =
                chocolateService.findAllChocolates(search, pageable);
        model.addAttribute(CHOCOLATES, chocolateDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return CHOCOLATE_CHOCOLATES;
    }

    @PostMapping(ADD_CHOCOLATE)
    public String create(@ModelAttribute @Validated ChocolateDTO chocolateDTO,
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
            return CHOCOLATE_ADD_CHOCOLATE;
        } else {
            chocolateService.createChocolate(chocolateDTO);
            return REDIRECT_API_CHOCOLATES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated ChocolateDTO chocolateDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(CHOCOLATE, chocolateDTO);
            model.addAttribute(BRANDS, brandService.findAllBrands());
            model.addAttribute(STORES, storeService.findAllStores());
            model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return CHOCOLATE_EDIT_CHOCOLATE;
        } else {
            chocolateService.updateChocolate(id, chocolateDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return REDIRECT_API_CHOCOLATES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!chocolateService.deleteChocolate(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_CHOCOLATE_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return chocolateService.findChocolateById(id)
                .map(chocolateDTO -> {
                    model.addAttribute(CHOCOLATE, chocolateDTO);
                    model.addAttribute(BRANDS, brandService.findAllBrands());
                    model.addAttribute(STORES, storeService.findAllStores());
                    model.addAttribute(SUPPLIERS, supplierService.findAllSuppliers());
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return CHOCOLATE_EDIT_CHOCOLATE;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_CHOCOLATE)
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
        return CHOCOLATE_ADD_CHOCOLATE;
    }
}
