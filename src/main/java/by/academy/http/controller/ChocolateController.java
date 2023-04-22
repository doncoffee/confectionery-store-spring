package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.ChocolateService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.dto.ChocolateDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/chocolates")
@RequiredArgsConstructor
public class ChocolateController {

    private final ChocolateService chocolateService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model) {
        List<ChocolateDTO> chocolateDTOList =
                chocolateService.findAllChocolates();
        model.addAttribute("chocolates", chocolateDTOList);
        return "chocolate/chocolates";
    }

    @PostMapping("/add_chocolate")
    public String create(@ModelAttribute ChocolateDTO chocolateDTO) {
        chocolateService.createChocolate(chocolateDTO);
        return "redirect:/api/chocolates";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute ChocolateDTO chocolateDTO) {
        chocolateService.updateChocolate(id, chocolateDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/chocolates";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!chocolateService.deleteChocolate(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/chocolates";
    }

    @GetMapping("/edit_chocolate/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return chocolateService.findChocolateById(id)
                .map(chocolateDTO -> {
                    model.addAttribute("chocolate", chocolateDTO);
                    model.addAttribute("brands", brandService.findAllBrands());
                    model.addAttribute("stores", storeService.findAllStores());
                    model.addAttribute("suppliers", supplierService.findAllSuppliers());
                    return "chocolate/edit-chocolate";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_chocolate")
    public String goToAddPage(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("stores", storeService.findAllStores());
        model.addAttribute("suppliers", supplierService.findAllSuppliers());
        return "chocolate/add-chocolate";
    }
}
