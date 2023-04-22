package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.dto.AddressDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public String findAll(Model model) {
        List<AddressDTO> addressDTOList = addressService.findAllAddresses();
        model.addAttribute("addresses", addressDTOList);
        return "address/addresses";
    }

    @PostMapping("/add_address")
    public String create(@ModelAttribute AddressDTO addressDTO) {
        addressService.createAddress(addressDTO);
        return "redirect:/api/addresses";
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AddressDTO addressDTO) {
        addressService.updateAddress(id, addressDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/addresses";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id) {
        if (!addressService.deleteAddress(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/addresses";
    }

    @GetMapping("/edit_address/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return addressService.findAddressById(id)
                .map(addressDTO -> {
                    model.addAttribute("address", addressDTO);
                    return "address/edit-address";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_address")
    public String goToAddPage() {
        return "address/add-address";
    }
}
