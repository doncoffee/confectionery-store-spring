package by.academy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "address")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @Column
    private String name;

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<Supplier> suppliers = new ArrayList<>();
}
