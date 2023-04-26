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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Objects;

@Controller
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable,
                          @RequestParam(value = "search", required = false) String search) {
        Page<SupplierDTO> supplierDTOPage =
                supplierService.findAllSuppliers(search, pageable);
        model.addAttribute("suppliers", supplierDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        model.addAttribute("search", search);
        return "supplier/suppliers";
    }

    @PostMapping("/add_supplier")
    public String create(@ModelAttribute SupplierDTO supplierDTO,
                         @RequestParam("page") Integer page,
                         @RequestParam("size") Integer size,
                         @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        supplierService.createSupplier(supplierDTO);
        return "redirect:/api/suppliers?page=" + page + "&size=" + size + "&search=" + search;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute SupplierDTO supplierDTO,
                         @RequestParam("page") Integer page,
                         @RequestParam("size") Integer size,
                         @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        supplierService.updateSupplier(id, supplierDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/suppliers?page=" + page + "&size=" + size + "&search=" + search;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!supplierService.deleteSupplier(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_supplier/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") Integer page,
                               @RequestParam("size") Integer size,
                               @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        return supplierService.findSupplierById(id)
                .map(supplierDTO -> {
                    model.addAttribute("supplier", supplierDTO);
                    model.addAttribute("addresses", addressService.findAllAddresses());
                    model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    model.addAttribute("search", search);
                    return "supplier/edit-supplier";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_supplier")
    public String goToAddPage(Model model,
                              @RequestParam("page") Integer page,
                              @RequestParam("size") Integer size,
                              @RequestParam(value = "search", required = false) String search) {
        Objects.requireNonNull(page, "Page parameter must not be null");
        Objects.requireNonNull(size, "Size parameter must not be null");
        model.addAttribute("addresses", addressService.findAllAddresses());
        model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        model.addAttribute("search", search);
        return "supplier/add-supplier";
    }
}
