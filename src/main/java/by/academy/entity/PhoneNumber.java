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
@Table(name = PHONE_NUMBER)
public class PhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = PHONE_NUMBER_ID)
    private Long id;

    @Column
    private String number;

    @OneToMany(mappedBy = PHONE_NUMBER1, cascade = CascadeType.ALL)
    private List<Store> stores = new ArrayList<>();

    @OneToMany(mappedBy = PHONE_NUMBER1, cascade = CascadeType.ALL)
    private List<Supplier> suppliers = new ArrayList<>();
}
