package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private String sessionId;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    public void addChocolate(Chocolate chocolate, Integer quantity) {
        Optional<CartItem> optionalCartItem = items.stream()
                .filter(item -> item.getChocolate() != null && item.getChocolate().getId().equals(chocolate.getId()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .chocolate(chocolate)
                    .quantity(quantity)
                    .cart(this)
                    .build();
            items.add(cartItem);
        }
    }

    public void addCookie(Cookie cookie, Integer quantity) {
        Optional<CartItem> optionalCartItem = items.stream()
                .filter(item -> item.getCookie() != null && item.getCookie().getId().equals(cookie.getId()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .cookie(cookie)
                    .quantity(quantity)
                    .cart(this)
                    .build();
            items.add(cartItem);
        }
    }

    public void addSweets(Sweets sweets, Integer quantity) {
        Optional<CartItem> optionalCartItem = items.stream()
                .filter(item -> item.getSweets() != null && item.getSweets().getId().equals(sweets.getId()))
                .findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        } else {
            CartItem cartItem = CartItem.builder()
                    .sweets(sweets)
                    .quantity(quantity)
                    .cart(this)
                    .build();
            items.add(cartItem);
        }
    }

    public void updateItemQuantity(Long cartItemId, Integer quantity) {
        Optional<CartItem> optionalCartItem = items.stream().filter(item -> item.getId().equals(cartItemId)).findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            cartItem.setQuantity(quantity);
        } else {
            throw new EntityNotFoundException("CartItem not found");
        }
    }

    public void removeItem(Long cartItemId) {
        Optional<CartItem> optionalCartItem = items.stream().filter(item -> item.getId().equals(cartItemId)).findFirst();
        if (optionalCartItem.isPresent()) {
            CartItem cartItem = optionalCartItem.get();
            items.remove(cartItem);
        } else {
            throw new EntityNotFoundException("CartItem not found");
        }
    }

    public void clear() {
        items.clear();
    }
}
