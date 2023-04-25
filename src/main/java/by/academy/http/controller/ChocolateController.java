package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.ChocolateService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.dto.ChocolateDTO;
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
@RequestMapping("/api/chocolates")
@RequiredArgsConstructor
public class ChocolateController {

    private final ChocolateService chocolateService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<ChocolateDTO> chocolateDTOPage =
                chocolateService.findAllChocolates(pageable);
        model.addAttribute("chocolates", chocolateDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "chocolate/chocolates";
    }

    @PostMapping("/add_chocolate")
    public String create(@ModelAttribute ChocolateDTO chocolateDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        chocolateService.createChocolate(chocolateDTO);
        return "redirect:/api/chocolates?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute ChocolateDTO chocolateDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        chocolateService.updateChocolate(id, chocolateDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/chocolates?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!chocolateService.deleteChocolate(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_chocolate/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return chocolateService.findChocolateById(id)
                .map(chocolateDTO -> {
                    model.addAttribute("chocolate", chocolateDTO);
                    model.addAttribute("brands", brandService.findAllBrands());
                    model.addAttribute("stores", storeService.findAllStores());
                    model.addAttribute("suppliers", supplierService.findAllSuppliers());
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "chocolate/edit-chocolate";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_chocolate")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("stores", storeService.findAllStores());
        model.addAttribute("suppliers", supplierService.findAllSuppliers());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "chocolate/add-chocolate";
    }
}
