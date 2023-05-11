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
@Table(name = STORE)
public class Store {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = STORE_ID)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ADDRESS_ID)
    private Address address;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PHONE_NUMBER_ID)
    private PhoneNumber phoneNumber;

    @Builder.Default
    @OneToMany(mappedBy = STORE, cascade = CascadeType.ALL)
    private List<Chocolate> chocolates = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = STORE, cascade = CascadeType.ALL)
    private List<Cookie> cookies = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = STORE, cascade = CascadeType.ALL)
    private List<Sweets> sweets = new ArrayList<>();
}
