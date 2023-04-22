package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.SweetsService;
import by.academy.service.dto.SweetsDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetsController {

    private final SweetsService sweetsService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model) {
        List<SweetsDTO> sweetsDTOList =
                sweetsService.findAllSweets();
        model.addAttribute("sweets", sweetsDTOList);
        return "sweets/sweets";
    }

    @PostMapping("/add_sweets")
    public String create(@ModelAttribute SweetsDTO sweetsDTO) {
        sweetsService.createSweets(sweetsDTO);
        return "redirect:/api/sweets";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute SweetsDTO sweetsDTO) {
        sweetsService.updateSweets(id, sweetsDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/sweets";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!sweetsService.deleteSweets(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/sweets";
    }

    @GetMapping("/edit_sweets/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return sweetsService.findSweetsById(id)
                .map(sweetsDTO -> {
                    model.addAttribute("sweets", sweetsDTO);
                    model.addAttribute("brands", brandService.findAllBrands());
                    model.addAttribute("stores", storeService.findAllStores());
                    model.addAttribute("suppliers", supplierService.findAllSuppliers());
                    return "sweets/edit-sweets";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_sweets")
    public String goToAddPage(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("stores", storeService.findAllStores());
        model.addAttribute("suppliers", supplierService.findAllSuppliers());
        return "sweets/add-sweets";
    }
}
