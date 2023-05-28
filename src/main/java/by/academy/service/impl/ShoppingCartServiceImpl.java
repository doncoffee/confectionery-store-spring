package by.academy.service.impl;

import by.academy.entity.*;
import by.academy.repository.ChocolateRepository;
import by.academy.repository.CookieRepository;
import by.academy.repository.ShoppingCartRepository;
import by.academy.repository.SweetsRepository;
import by.academy.service.ShoppingCartService;
import by.academy.util.ShoppingCartUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static by.academy.util.Constants.*;
import static by.academy.util.ShoppingCartUtils.*;

@Service
@RequiredArgsConstructor
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ChocolateRepository chocolateRepository;
    private final CookieRepository cookieRepository;
    private final SweetsRepository sweetsRepository;

    @Override
    public ShoppingCart getOrCreateCart(String sessionId) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findBySessionId(sessionId);
        return optionalCart.orElseGet(() -> {
            ShoppingCart cart = new ShoppingCart();
            cart.setSessionId(sessionId);
            return shoppingCartRepository.save(cart);
        });
    }

    @Override
    public void addChocolateToCart(Long cartId, Long chocolateId, Integer quantity) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            Chocolate chocolate = chocolateRepository.findById(chocolateId)
                    .orElseThrow(() -> new EntityNotFoundException(CHOCOLATE_NOT_FOUND));
            addChocolate(cart, chocolate, quantity);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public void addCookieToCart(Long cartId, Long cookieId, Integer quantity) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            Cookie cookie = cookieRepository.findById(cookieId)
                    .orElseThrow(() -> new EntityNotFoundException(COOKIE_NOT_FOUND));
            addCookie(cart, cookie, quantity);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public void addSweetsToCart(Long cartId, Long sweetsId, Integer quantity) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            Sweets sweets = sweetsRepository.findById(sweetsId)
                    .orElseThrow(() -> new EntityNotFoundException(SWEETS_NOT_FOUND));
            addSweets(cart, sweets, quantity);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public void updateItemQuantity(Long cartId, Long cartItemId, Integer quantity) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            ShoppingCartUtils.updateItemQuantity(cart, cartItemId, quantity);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public void removeItemFromCart(Long cartId, Long cartItemId) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            removeItem(cart, cartItemId);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public void clearCart(Long cartId) {
        Optional<ShoppingCart> optionalCart = shoppingCartRepository.findById(cartId);
        if (optionalCart.isPresent()) {
            ShoppingCart cart = optionalCart.get();
            clear(cart);
            shoppingCartRepository.save(cart);
        } else {
            throw new EntityNotFoundException(SHOPPING_CART_NOT_FOUND);
        }
    }

    @Override
    public Double countTotalPrice(List<CartItem> items) {
        double totalPrice = 0;
        for (CartItem item : items) {
            double itemPrice;
            if (item.getChocolate() != null) {
                itemPrice = item.getChocolate().getPrice() * item.getQuantity();
            } else if (item.getCookie() != null) {
                itemPrice = item.getCookie().getPrice() * item.getQuantity();
            } else {
                itemPrice = item.getSweets().getPrice() * item.getQuantity();
            }
            totalPrice += itemPrice;
        }
        return totalPrice;
    }
}