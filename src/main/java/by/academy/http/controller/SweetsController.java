package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.SweetsService;
import by.academy.service.dto.SweetsDTO;
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
@RequestMapping("/api/sweets")
@RequiredArgsConstructor
public class SweetsController {

    private final SweetsService sweetsService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<SweetsDTO> sweetsDTOPage =
                sweetsService.findAllSweets(pageable);
        model.addAttribute("sweets", sweetsDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "sweets/sweets";
    }

    @PostMapping("/add_sweets")
    public String create(@ModelAttribute SweetsDTO sweetsDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        sweetsService.createSweets(sweetsDTO);
        return "redirect:/api/sweets?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute SweetsDTO sweetsDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        sweetsService.updateSweets(id, sweetsDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/sweets?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!sweetsService.deleteSweets(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_sweets/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return sweetsService.findSweetsById(id)
                .map(sweetsDTO -> {
                    model.addAttribute("sweets", sweetsDTO);
                    model.addAttribute("brands", brandService.findAllBrands());
                    model.addAttribute("stores", storeService.findAllStores());
                    model.addAttribute("suppliers", supplierService.findAllSuppliers());
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "sweets/edit-sweets";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_sweets")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("stores", storeService.findAllStores());
        model.addAttribute("suppliers", supplierService.findAllSuppliers());
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "sweets/add-sweets";
    }
}
