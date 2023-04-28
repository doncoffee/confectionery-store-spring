package by.academy.http.controller;

import by.academy.service.PhoneNumberService;
import by.academy.service.dto.PhoneNumberDTO;
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

@Controller
@RequestMapping("/api/phone_numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = "search", required = false) String search) {
        Page<PhoneNumberDTO> phoneNumberDTOPage =
                phoneNumberService.findAllPhoneNumbers(search, pageable);
        model.addAttribute("phoneNumbers", phoneNumberDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("search", search);
        return "contacts/phone-numbers";
    }

    @PostMapping("/add_phone_number")
    public String create(@ModelAttribute @Validated PhoneNumberDTO phoneNumberDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam("page") Integer page,
                         @RequestParam("size") Integer size,
                         @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("search", search);
            return "contacts/add-phone-number";
        } else {
            phoneNumberService.createPhoneNumber(phoneNumberDTO);
            return "redirect:/api/phone_numbers?page=" + page + "&size=" + size + "&search=" + search;
        }
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute @Validated PhoneNumberDTO phoneNumberDTO,
                         BindingResult bindingResult,
                         Model model,
                         @RequestParam("page") Integer page,
                         @RequestParam("size") Integer size,
                         @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            model.addAttribute("phoneNumber", phoneNumberDTO);
            model.addAttribute("page", page);
            model.addAttribute("size", size);
            model.addAttribute("search", search);
            return "contacts/edit-phone-number";
        } else {
            phoneNumberService.updatePhoneNumber(id, phoneNumberDTO)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND));
            return "redirect:/api/phone_numbers?page=" + page + "&size=" + size + "&search=" + search;
        }
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!phoneNumberService.deletePhoneNumber(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_phone_number/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") Integer page,
                               @RequestParam("size") Integer size,
                               @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        return phoneNumberService.findPhoneNumberById(id)
                .map(phoneNumberDTO -> {
                    model.addAttribute("phoneNumber", phoneNumberDTO);
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    model.addAttribute("search", search);
                    return "contacts/edit-phone-number";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_phone_number")
    public String goToAddPage(Model model,
                              @RequestParam("page") Integer page,
                              @RequestParam("size") Integer size,
                              @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        return "contacts/add-phone-number";
    }
}
