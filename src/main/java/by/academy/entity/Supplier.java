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
@Table(name = SUPPLIER)
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = SUPPLIER_ID)
    private Long id;

    @Column
    private String name;

    @Column(name = CONTACT_PERSON)
    private String contactPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ADDRESS_ID)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PHONE_NUMBER_ID)
    private PhoneNumber phoneNumber;

    @OneToMany(mappedBy = SUPPLIER, cascade = CascadeType.ALL)
    private List<Chocolate> chocolates = new ArrayList<>();

    @OneToMany(mappedBy = SUPPLIER, cascade = CascadeType.ALL)
    private List<Cookie> cookies = new ArrayList<>();

    @OneToMany(mappedBy = SUPPLIER, cascade = CascadeType.ALL)
    private List<Sweets> sweets = new ArrayList<>();
}
