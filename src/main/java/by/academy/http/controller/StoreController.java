package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.PhoneNumberService;
import by.academy.service.StoreService;
import by.academy.service.dto.StoreDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model) {
        List<StoreDTO> storeDTOList =
                storeService.findAllStores();
        model.addAttribute("stores", storeDTOList);
        return "store/stores";
    }

    @PostMapping("/add_store")
    public String create(@ModelAttribute StoreDTO storeDTO) {
        storeService.createStore(storeDTO);
        return "redirect:/api/stores";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute StoreDTO storeDTO) {
        storeService.updateStore(id, storeDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/stores";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!storeService.deleteStore(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/stores";
    }

    @GetMapping("/edit_store/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return storeService.findStoreById(id)
                .map(storeDTO -> {
                    model.addAttribute("store", storeDTO);
                    model.addAttribute("addresses", addressService.findAllAddresses());
                    model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
                    return "store/edit-store";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_store")
    public String goToAddPage(Model model) {
        model.addAttribute("addresses", addressService.findAllAddresses());
        model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
        return "store/add-store";
    }
}
