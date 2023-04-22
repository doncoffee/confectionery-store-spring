package by.academy.http.controller;

import by.academy.service.PhoneNumberService;
import by.academy.service.dto.PhoneNumberDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/phone_numbers")
@RequiredArgsConstructor
public class PhoneNumberController {

    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model) {
        List<PhoneNumberDTO> phoneNumberDTOList =
                phoneNumberService.findAllPhoneNumbers();
        model.addAttribute("phoneNumbers", phoneNumberDTOList);
        return "contacts/phone-numbers";
    }

    @PostMapping("/add_phone_number")
    public String create(@ModelAttribute PhoneNumberDTO phoneNumberDTO) {
        phoneNumberService.createPhoneNumber(phoneNumberDTO);
        return "redirect:/api/phone_numbers";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute PhoneNumberDTO phoneNumberDTO) {
        phoneNumberService.updatePhoneNumber(id, phoneNumberDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/phone_numbers";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!phoneNumberService.deletePhoneNumber(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/phone_numbers";
    }

    @GetMapping("/edit_phone_number/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return phoneNumberService.findPhoneNumberById(id)
                .map(phoneNumberDTO -> {
                    model.addAttribute("phoneNumber", phoneNumberDTO);
                    return "contacts/edit-phone-number";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_phone_number")
    public String goToAddPage() {
        return "contacts/add-phone-number";
    }
}
