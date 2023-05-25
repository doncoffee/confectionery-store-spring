package by.academy.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static by.academy.util.Constants.ADDRESS;
import static by.academy.util.Constants.ADDRESS_ID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = ADDRESS)
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = ADDRESS_ID)
    private Long id;

    @Column
    private String name;

    @OneToMany(mappedBy = ADDRESS, cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = ADDRESS, cascade = CascadeType.ALL)
    private List<Supplier> suppliers = new ArrayList<>();
}
