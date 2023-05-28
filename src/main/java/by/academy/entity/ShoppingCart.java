package by.academy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.*;

import static by.academy.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = SHOPPING_CART)
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = SESSION_ID, nullable = false)
    private String sessionId;

    @Builder.Default
    @ToString.Exclude
    @OneToMany(mappedBy = CART, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();
}
