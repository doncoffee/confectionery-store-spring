package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static by.academy.util.Constants.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = SWEETS)
public class Sweets {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SWEETS_ID)
    private Long id;

    @Column
    private Double price;

    @Column
    private String type;

    @Column
    private Double weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = BRAND_ID)
    private Brand brand;

    @Column
    private String composition;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = STORE_ID)
    private Store store;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = SUPPLIER_ID)
    private Supplier supplier;

    @OneToMany(mappedBy = SWEETS, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> cartItems = new ArrayList<>();
}
