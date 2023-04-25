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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<AddressDTO> addressDTOPage = addressService.findAllAddresses(pageable);
        model.addAttribute("addresses", addressDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "address/addresses";
    }

    @PostMapping("/add_address")
    public String create(@ModelAttribute AddressDTO addressDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        addressService.createAddress(addressDTO);
        return "redirect:/api/addresses?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable("id") Long id,
                         @ModelAttribute AddressDTO addressDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        addressService.updateAddress(id, addressDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/addresses?page=" + page + "&size=" + size;
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable("id") Long id,
                         HttpServletRequest request) {
        if (!addressService.deleteAddress(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_address/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return addressService.findAddressById(id)
                .map(addressDTO -> {
                    model.addAttribute("address", addressDTO);
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "address/edit-address";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_address")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "address/add-address";
    }
}
