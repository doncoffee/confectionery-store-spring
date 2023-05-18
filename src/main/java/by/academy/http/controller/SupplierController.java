package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.PhoneNumberService;
import by.academy.service.SupplierService;
import by.academy.service.dto.SupplierDTO;
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
@RequestMapping(API_SUPPLIERS)
@RequiredArgsConstructor
public class SupplierController {
    private final SupplierService supplierService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<SupplierDTO> supplierDTOPage =
                supplierService.findAllSuppliers(search, pageable);
        model.addAttribute(SUPPLIERS, supplierDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return SUPPLIER_SUPPLIERS;
    }

    @PostMapping(ADD_SUPPLIER)
    public String create(@ModelAttribute @Validated SupplierDTO supplierDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(ADDRESSES, addressService.findAllAddresses());
            model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return SUPPLIER_ADD_SUPPLIER;
        } else {
            supplierService.createSupplier(supplierDTO);
            return REDIRECT_API_SUPPLIERS_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated SupplierDTO supplierDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(SUPPLIER, supplierDTO);
            model.addAttribute(ADDRESSES, addressService.findAllAddresses());
            model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return SUPPLIER_EDIT_SUPPLIER;
        } else {
            supplierService.updateSupplier(id, supplierDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return REDIRECT_API_SUPPLIERS_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!supplierService.deleteSupplier(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_SUPPLIER_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return supplierService.findSupplierById(id)
                .map(supplierDTO -> {
                    model.addAttribute(SUPPLIER, supplierDTO);
                    model.addAttribute(ADDRESSES, addressService.findAllAddresses());
                    model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return SUPPLIER_EDIT_SUPPLIER;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_SUPPLIER)
    public String goToAddPage(Model model,
                              @RequestParam(PAGE) Integer page,
                              @RequestParam(SIZE) Integer size,
                              @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        model.addAttribute(ADDRESSES, addressService.findAllAddresses());
        model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
        model.addAttribute(PAGE, page);
        model.addAttribute(SIZE, size);
        model.addAttribute(SEARCH, search);
        return SUPPLIER_ADD_SUPPLIER;
    }
}
