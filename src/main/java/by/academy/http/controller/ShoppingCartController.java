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

import static by.academy.util.Constants.*;

@Controller
@RequiredArgsConstructor
@RequestMapping(API_CART)
public class ShoppingCartController {
    private final ShoppingCartService shoppingCartService;

    @GetMapping
    public String showCart(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        model.addAttribute(CART, cart);
        model.addAttribute(TOTAL_PRICE, shoppingCartService.countTotalPrice(cart.getItems()));
        return CART;
    }

    @PostMapping(CHOCOLATE_ID_ADD_CHOCOLATE_TO_CART)
    public String addChocolateToCart(HttpServletRequest request,
                                                @PathVariable Long chocolateId,
                                                @RequestParam(QUANTITY) Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addChocolateToCart(cart.getId(), chocolateId, quantity);
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(COOKIE_ID_ADD_COOKIE_TO_CART)
    public String addCookieToCart(HttpServletRequest request,
                                     @PathVariable Long cookieId,
                                     @RequestParam(QUANTITY) Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addCookieToCart(cart.getId(), cookieId, quantity);
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(SWEETS_ID_ADD_SWEETS_TO_CART)
    public String addSweetsToCart(HttpServletRequest request,
                                     @PathVariable Long sweetsId,
                                     @RequestParam(QUANTITY) Integer quantity) {
        HttpSession session = request.getSession();
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
        ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
        shoppingCartService.addSweetsToCart(cart.getId(), sweetsId, quantity);
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(CART_ID_REMOVE_CART_ITEM_ID)
    public String removeItemFromCart(HttpServletRequest request,
                                     @PathVariable Long cartId,
                                     @PathVariable Long cartItemId) {
        shoppingCartService.removeItemFromCart(cartId, cartItemId);
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(CLEAR_CART)
    public String clearCart(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
            ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
            if (cart != null) {
                shoppingCartService.clearCart(cart.getId());
            }
        }
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(CART_ID_UPDATE_CART_ITEM_ID)
    public String updateItemQuantity(HttpServletRequest request,
                                     @PathVariable Long cartId,
                                     @PathVariable Long cartItemId,
                                     @RequestParam Integer quantity) {
        shoppingCartService.updateItemQuantity(cartId, cartItemId, quantity);
        String referer = request.getHeader(REFERER);
        return REDIRECT + referer;
    }

    @PostMapping(PURCHASE_SUCCESSFUL1)
    public String buy(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String sessionId = authentication != null && !ANONYMOUS_USER.equals(authentication.getName()) ? authentication.getName() : session.getId();
            ShoppingCart cart = shoppingCartService.getOrCreateCart(sessionId);
            if (cart != null) {
                shoppingCartService.clearCart(cart.getId());
            }
        }
        return PURCHASE_SUCCESSFUL;
    }
}
