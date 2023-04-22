package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.CookieService;
import by.academy.service.StoreService;
import by.academy.service.SupplierService;
import by.academy.service.dto.CookieDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/cookies")
@RequiredArgsConstructor
public class CookieController {

    private final CookieService cookieService;
    private final BrandService brandService;
    private final StoreService storeService;
    private final SupplierService supplierService;

    @GetMapping
    public String findAll(Model model) {
        List<CookieDTO> cookieDTOList =
                cookieService.findAllCookies();
        model.addAttribute("cookies", cookieDTOList);
        return "cookie/cookies";
    }

    @PostMapping("/add_cookie")
    public String create(@ModelAttribute CookieDTO cookieDTO) {
        cookieService.createCookie(cookieDTO);
        return "redirect:/api/cookies";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute CookieDTO cookieDTO) {
        cookieService.updateCookie(id, cookieDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        return "redirect:/api/cookies";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!cookieService.deleteCookie(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/cookies";
    }

    @GetMapping("/edit_cookie/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return cookieService.findCookieById(id)
                .map(cookieDTO -> {
                    model.addAttribute("cookie", cookieDTO);
                    model.addAttribute("brands", brandService.findAllBrands());
                    model.addAttribute("stores", storeService.findAllStores());
                    model.addAttribute("suppliers", supplierService.findAllSuppliers());
                    return "cookie/edit-cookie";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_cookie")
    public String goToAddPage(Model model) {
        model.addAttribute("brands", brandService.findAllBrands());
        model.addAttribute("stores", storeService.findAllStores());
        model.addAttribute("suppliers", supplierService.findAllSuppliers());
        return "cookie/add-cookie";
    }
}
