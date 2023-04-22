package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.dto.BrandDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Controller
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public String findAll(Model model) {
        List<BrandDTO> brandDTOList = brandService.findAllBrands();
        model.addAttribute("brands", brandDTOList);
        return "brand/brands";
    }

    @PostMapping("add_brand")
    public String create(@ModelAttribute BrandDTO brandDTO) {
        brandService.createBrand(brandDTO);
        return "redirect:/api/brands";
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute BrandDTO brandDTO) {
        brandService.updateBrand(id, brandDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/brands";
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id) {
        if (!brandService.deleteBrand(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return "redirect:/api/brands";
    }

    @GetMapping("/edit_brand/{id}")
    public String goToEditPage(@PathVariable Long id, Model model) {
        return brandService.findBrandById(id)
                .map(brandDTO -> {
                    model.addAttribute("brand", brandDTO);
                    return "brand/edit-brand";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_brand")
    public String goToAddPage() {
        return "brand/add-brand";
    }
}
