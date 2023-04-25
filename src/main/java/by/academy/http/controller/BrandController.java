package by.academy.http.controller;

import by.academy.service.BrandService;
import by.academy.service.dto.BrandDTO;
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
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @GetMapping
    public String findAll(Model model,
                          @PageableDefault(size = 3) Pageable pageable) {
        Page<BrandDTO> brandDTOPage = brandService.findAllBrands(pageable);
        model.addAttribute("brands", brandDTOPage);
        model.addAttribute("page", pageable.getPageNumber());
        model.addAttribute("size", pageable.getPageSize());
        return "brand/brands";
    }

    @PostMapping("add_brand")
    public String create(@ModelAttribute BrandDTO brandDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        brandService.createBrand(brandDTO);
        return "redirect:/api/brands?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/update")
    public String update(@PathVariable Long id,
                         @ModelAttribute BrandDTO brandDTO,
                         @RequestParam("page") int page,
                         @RequestParam("size") int size) {
        brandService.updateBrand(id, brandDTO)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
        return "redirect:/api/brands?page=" + page + "&size=" + size;
    }

    @PostMapping("{id}/delete")
    public String delete(@PathVariable Long id,
                         HttpServletRequest request) {
        if (!brandService.deleteBrand(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @GetMapping("/edit_brand/{id}")
    public String goToEditPage(@PathVariable Long id,
                               Model model,
                               @RequestParam("page") int page,
                               @RequestParam("size") int size) {
        return brandService.findBrandById(id)
                .map(brandDTO -> {
                    model.addAttribute("brand", brandDTO);
                    model.addAttribute("page", page);
                    model.addAttribute("size", size);
                    return "brand/edit-brand";
                })
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND));
    }

    @GetMapping("/add_brand")
    public String goToAddPage(Model model,
                              @RequestParam("page") int page,
                              @RequestParam("size") int size) {
        model.addAttribute("page", page);
        model.addAttribute("size", size);
        return "brand/add-brand";
    }
}
