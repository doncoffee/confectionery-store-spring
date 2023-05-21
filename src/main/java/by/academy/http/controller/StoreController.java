package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.PhoneNumberService;
import by.academy.service.StoreService;
import by.academy.service.dto.StoreDTO;
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
@RequestMapping(API_STORES)
@RequiredArgsConstructor
public class StoreController {
    private final StoreService storeService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<StoreDTO> storeDTOPage =
                storeService.findAllStores(search, pageable);
        model.addAttribute(STORES, storeDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return STORE_STORES;
    }

    @PostMapping(ADD_STORE)
    public String create(@ModelAttribute @Validated StoreDTO storeDTO,
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
            return STORE_ADD_STORE;
        } else {
            storeService.createStore(storeDTO);
            return REDIRECT_API_STORES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated StoreDTO storeDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(STORE, storeDTO);
            model.addAttribute(ADDRESSES, addressService.findAllAddresses());
            model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return STORE_EDIT_STORE;
        } else {
            storeService.updateStore(id, storeDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return REDIRECT_API_STORES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!storeService.deleteStore(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_STORE_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return storeService.findStoreById(id)
                .map(storeDTO -> {
                    model.addAttribute(STORE, storeDTO);
                    model.addAttribute(ADDRESSES, addressService.findAllAddresses());
                    model.addAttribute(PHONE_NUMBERS, phoneNumberService.findAllPhoneNumbers());
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return STORE_EDIT_STORE;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_STORE)
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
        return STORE_ADD_STORE;
    }
}
