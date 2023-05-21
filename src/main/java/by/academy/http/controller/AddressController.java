package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.dto.AddressDTO;
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
@RequestMapping(API_ADDRESSES)
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = SEARCH, required = false) String search) {
        Page<AddressDTO> addressDTOPage = addressService.findAllAddresses(search, pageable);
        model.addAttribute(ADDRESSES, addressDTOPage);
        model.addAttribute(PAGE, pageable.getPageNumber());
        model.addAttribute(SIZE, pageable.getPageSize());
        model.addAttribute(SEARCH, search);
        return ADDRESS_ADDRESSES;
    }

    @PostMapping(ADD_ADDRESS)
    public String create(@ModelAttribute @Validated AddressDTO addressDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return ADDRESS_ADD_ADDRESS;
        } else {
            addressService.createAddress(addressDTO);
            return REDIRECT_API_ADDRESSES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_UPDATE)
    public String update(@PathVariable(ID) Long id,
                         @ModelAttribute @Validated AddressDTO addressDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam(PAGE) Integer page,
                         @RequestParam(SIZE) Integer size,
                         @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        if (bindingResult.hasErrors()) {
            model.addAttribute(ERRORS, bindingResult.getAllErrors());
            model.addAttribute(ADDRESS, addressDTO);
            model.addAttribute(PAGE, page);
            model.addAttribute(SIZE, size);
            model.addAttribute(SEARCH, search);
            return ADDRESS_EDIT_ADDRESS;
        } else {
            addressService.updateAddress(id, addressDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return REDIRECT_API_ADDRESSES_PAGE + page + SIZE_PATH + size + SEARCH_PATH + search;
        }
    }

    @PostMapping(ID_DELETE)
    public String delete(@PathVariable(ID) Long id,
                         HttpServletRequest request) {
        if (!addressService.deleteAddress(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @GetMapping(EDIT_ADDRESS_ID)
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam(PAGE) Integer page,
                               @RequestParam(SIZE) Integer size,
                               @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        return addressService.findAddressById(id)
                .map(addressDTO -> {
                    model.addAttribute(ADDRESS, addressDTO);
                    model.addAttribute(PAGE, page);
                    model.addAttribute(SIZE, size);
                    model.addAttribute(SEARCH, search);
                    return ADDRESS_EDIT_ADDRESS;
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping(ADD_ADDRESS)
    public String goToAddPage(Model model,
                              @RequestParam(PAGE) Integer page,
                              @RequestParam(SIZE) Integer size,
                              @RequestParam(value = SEARCH, required = false) String search) {
        Objects.requireNonNull(page, PAGE_PARAMETER_MUST_NOT_BE_NULL);
        Objects.requireNonNull(size, SIZE_PARAMETER_MUST_NOT_BE_NULL);
        model.addAttribute(PAGE, page);
        model.addAttribute(SIZE, size);
        model.addAttribute(SEARCH, search);
        return ADDRESS_ADD_ADDRESS;
    }
}
