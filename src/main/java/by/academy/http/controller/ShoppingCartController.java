package by.academy.http.controller;

import by.academy.entity.ShoppingCart;
import by.academy.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class ShoppingCartController {

    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public String showCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        model.addAttribute("cart", cart);
        model.addAttribute("totalPrice", shoppingCartService.countTotalPrice(cart.getItems()));
        return "cart";
    }

    @PostMapping("/{chocolateId}/add_chocolate_to_cart")
    public String addChocolateToCart(HttpServletRequest request,
                                                @PathVariable Long chocolateId,
                                                @RequestParam("quantity") Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addChocolateToCart(cart.getId(), chocolateId, quantity);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/{cookieId}/add_cookie_to_cart")
    public String addCookieToCart(HttpServletRequest request,
                                     @PathVariable Long cookieId,
                                     @RequestParam("quantity") Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addCookieToCart(cart.getId(), cookieId, quantity);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/{sweetsId}/add_sweets_to_cart")
    public String addSweetsToCart(HttpServletRequest request,
                                     @PathVariable Long sweetsId,
                                     @RequestParam("quantity") Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addSweetsToCart(cart.getId(), sweetsId, quantity);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/{cartId}/remove/{cartItemId}")
    public String removeItemFromCart(HttpServletRequest request,
                                     @PathVariable Long cartId,
                                     @PathVariable Long cartItemId) {
        shoppingCartService.removeItemFromCart(cartId, cartItemId);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/clear_cart")
    public String clearCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
            ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
            if (cart != null) {
                shoppingCartService.clearCart(cart.getId());
            }
        }
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/{cartId}/update/{cartItemId}")
    public String updateItemQuantity(HttpServletRequest request,
                                     @PathVariable Long cartId,
                                     @PathVariable Long cartItemId,
                                     @RequestParam Integer quantity) {
        shoppingCartService.updateItemQuantity(cartId, cartItemId, quantity);
        String referer = request.getHeader("Referer");
        return "redirect:" + referer;
    }

    @PostMapping("/purchase_successful")
    public String buy(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String sessionId = authentication != null && !"anonymousUser".equals(authentication.getName()) ? authentication.getName() : session.getId();
            ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
            if (cart != null) {
                shoppingCartService.clearCart(cart.getId());
            }
        }
        return "purchase-successful";
    }
}
