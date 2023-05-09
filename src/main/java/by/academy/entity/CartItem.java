package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "cart_item")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chocolate_id")
    private Chocolate chocolate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cookie_id")
    private Cookie cookie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sweets_id")
    private Sweets sweets;

    @Column(nullable = false)
    private Integer quantity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private ShoppingCart cart;
}