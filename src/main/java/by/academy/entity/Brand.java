package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static by.academy.util.Constants.BRAND;
import static by.academy.util.Constants.BRAND_ID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = BRAND)
public class Brand {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = BRAND_ID)
    private Long id;

    @Column
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = BRAND, cascade = CascadeType.ALL)
    private List<Chocolate> chocolates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = BRAND, cascade = CascadeType.ALL)
    private List<Cookie> cookies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = BRAND, cascade = CascadeType.ALL)
    private List<Sweets> sweets = new ArrayList<>();
}
