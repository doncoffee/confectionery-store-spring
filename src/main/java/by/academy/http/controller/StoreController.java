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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/api/stores")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<StoreDTO> storeDTOPage =
                storeService.findAllStores(pageable);
        model.addAttribute("stores", storeDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "store/stores";
    }

    @PostMapping("/add_store")
    public String create(@ModelAttribute StoreDTO storeDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        storeService.createStore(storeDTO);
        return "redirect:/api/stores?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute StoreDTO storeDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        storeService.updateStore(id, storeDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/stores?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!storeService.deleteStore(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_store/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return storeService.findStoreById(id)
                .map(storeDTO -> {
                    model.addAttribute("store", storeDTO);
                    model.addAttribute("addresses", addressService.findAllAddresses());
                    model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "store/edit-store";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_store")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("addresses", addressService.findAllAddresses());
        model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "store/add-store";
    }
}
