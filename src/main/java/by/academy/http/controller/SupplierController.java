package by.academy.http.controller;

import by.academy.service.AddressService;
import by.academy.service.PhoneNumberService;
import by.academy.service.SupplierService;
import by.academy.service.dto.SupplierDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;
    private final AddressService addressService;
    private final PhoneNumberService phoneNumberService;

    @GetMapping
    public String findAll(Model model) {
        List<SupplierDTO> supplierDTOList =
                supplierService.findAllSuppliers();
        model.addAttribute("suppliers", supplierDTOList);
        return "supplier/suppliers";
    }

    @PostMapping("/add_supplier")
    public String create(@ModelAttribute SupplierDTO supplierDTO) {
        supplierService.createSupplier(supplierDTO);
        return "redirect:/api/suppliers";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute SupplierDTO supplierDTO) {
        supplierService.updateSupplier(id, supplierDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/suppliers";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!supplierService.deleteSupplier(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/suppliers";
    }

    @GetMapping("/edit_supplier/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return supplierService.findSupplierById(id)
                .map(supplierDTO -> {
                    model.addAttribute("supplier", supplierDTO);
                    model.addAttribute("addresses", addressService.findAllAddresses());
                    model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
                    return "supplier/edit-supplier";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_supplier")
    public String goToAddPage(Model model) {
        model.addAttribute("addresses", addressService.findAllAddresses());
        model.addAttribute("phoneNumbers", phoneNumberService.findAllPhoneNumbers());
        return "supplier/add-supplier";
    }
}
