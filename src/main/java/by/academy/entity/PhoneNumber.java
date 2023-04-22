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
@Table(name = "phone_number")
public class PhoneNumber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_number_id")
    private Long id;

    @Column
    private String number;

    @Builder.Default
    @OneToMany(mappedBy = "phoneNumber", cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "phoneNumber", cascade = CascadeType.ALL)
    private List<Supplier> suppliers = new ArrayList<>();
}
