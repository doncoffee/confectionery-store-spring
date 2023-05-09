package by.academy.service;

import by.academy.entity.CartItem;
import by.academy.entity.ShoppingCart;

import java.util.List;

public interface ShoppingCartService {

    ShoppingCart getOrCreateCart(String sessionId);
    void addChocolateToCart(Long cartId, Long chocolateId, Integer quantity);
    void addCookieToCart(Long cartId, Long cookieId, Integer quantity);
    void addSweetsToCart(Long cartId, Long sweetsId, Integer quantity);
    void updateItemQuantity(Long cartId, Long cartItemId, Integer quantity);
    void removeItemFromCart(Long cartId, Long cartItemId);
    void clearCart(Long cartId);
    Double countTotalPrice(List<CartItem> items);
}
