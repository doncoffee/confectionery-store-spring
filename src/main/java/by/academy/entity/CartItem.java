package by.academy.entity;

import jakarta.persistence.*;
import lombok.*;

import static by.academy.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = CART_ITEM)
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CHOCOLATE_ID)
    private Chocolate chocolate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = COOKIE_ID)
    private Cookie cookie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SWEETS_ID)
    private Sweets sweets;

    @Column(nullable = false)
    private Integer quantity;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CART_ID)
    private ShoppingCart cart;
}