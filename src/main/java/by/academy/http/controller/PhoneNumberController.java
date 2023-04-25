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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/phone_numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<PhoneNumberDTO> phoneNumberDTOPage =
                phoneNumberService.findAllPhoneNumbers(pageable);
        model.addAttribute("phoneNumbers", phoneNumberDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "contacts/phone-numbers";
    }

    @PostMapping("/add_phone_number")
    public String create(@ModelAttribute PhoneNumberDTO phoneNumberDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        phoneNumberService.createPhoneNumber(phoneNumberDTO);
        return "redirect:/api/phone_numbers?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute PhoneNumberDTO phoneNumberDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        phoneNumberService.updatePhoneNumber(id, phoneNumberDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/phone_numbers?page=" + page + "&size=" + size;
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
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return phoneNumberService.findPhoneNumberById(id)
                .map(phoneNumberDTO -> {
                    model.addAttribute("phoneNumber", phoneNumberDTO);
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "contacts/edit-phone-number";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_phone_number")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "contacts/add-phone-number";
    }
}
