package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "brand_id")
    private Long id;

    @Column
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Chocolate> chocolates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Cookie> cookies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL)
    private List<Sweets> sweets = new ArrayList<>();
}
