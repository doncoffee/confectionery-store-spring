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
@Table(name = "supplier")
public class Supplier {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "supplier_id")
    private Long id;

    @Column
    private String name;

    @Column(name = "contact_person")
    private String contactPerson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id")
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number_id")
    private PhoneNumber phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Chocolate> chocolates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Cookie> cookies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL)
    private List<Sweets> sweets = new ArrayList<>();
}
