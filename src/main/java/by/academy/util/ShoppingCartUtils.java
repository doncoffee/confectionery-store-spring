package by.academy.util;

import by.academy.entity.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.experimental.UtilityClass;

import java.util.List;
import java.util.Optional;

import static by.academy.util.Constants.CART_ITEM_NOT_FOUND;

@UtilityClass
public class ShoppingCartUtils {

    public static void addChocolate(ShoppingCart cart,
                                    Chocolate chocolate,
                                    Integer quantity) {
        Optional<CartItem> optionalCartItem =
                getCartItemByChocolate(cart.getItems(), chocolate);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .chocolate(chocolate)
                    .quantity(quantity)
                    .cart(cart)
                    .build();
            cart.getItems().add(cartItem);
        }
    }

    public static void addCookie(ShoppingCart cart,
                                 Cookie cookie,
                                 Integer quantity) {
        Optional<CartItem> optionalCartItem =
                getCartItemByCookie(cart.getItems(), cookie);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cookie(cookie)
                    .quantity(quantity)
                    .cart(cart)
                    .build();
            cart.getItems().add(cartItem);
        }
    }

    public static void addSweets(ShoppingCart cart,
                                 Sweets sweets,
                                 Integer quantity) {
        Optional<CartItem> optionalCartItem =
                getCartItemBySweets(cart.getItems(), sweets);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .sweets(sweets)
                    .quantity(quantity)
                    .cart(cart)
                    .build();
            cart.getItems().add(cartItem);
        }
    }

    public static void updateItemQuantity(ShoppingCart cart,
                                          Long cartItemId,
                                          Integer quantity) {
        Optional<CartItem> optionalCartItem = getCartItemById(cart.getItems(), cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND);
        }
    }

    public static void removeItem(ShoppingCart cart, Long cartItemId) {
        Optional<CartItem> optionalCartItem = getCartItemById(cart.getItems(), cartItemId);
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cart.getItems().remove(cartItem);
        } else {
            throw new EntityNotFoundException(CART_ITEM_NOT_FOUND);
        }
    }

    public static void clear(ShoppingCart cart) {
        cart.getItems().clear();
    }

    private Optional<CartItem> getCartItemByChocolate(List<CartItem> cartItems,
                                                      Chocolate chocolate) {
        return cartItems.stream()
                .filter(item -> item.getChocolate() != null
                        && item.getChocolate().getId().equals(chocolate.getId()))
                .findFirst();
    }

    private Optional<CartItem> getCartItemByCookie(List<CartItem> cartItems,
                                                   Cookie cookie) {
        return cartItems.stream()
                .filter(item -> item.getCookie() != null
                        && item.getCookie().getId().equals(cookie.getId()))
                .findFirst();
    }

    private Optional<CartItem> getCartItemBySweets(List<CartItem> cartItems,
                                                   Sweets sweets) {
        return cartItems.stream()
                .filter(item -> item.getSweets() != null
                        && item.getSweets().getId().equals(sweets.getId()))
                .findFirst();
    }

    private Optional<CartItem> getCartItemById(List<CartItem> cartItems,
                                               Long cartItemId) {
        return cartItems.stream()
                .filter(item -> item.getId().equals(cartItemId))
                .findFirst();
    }
}
