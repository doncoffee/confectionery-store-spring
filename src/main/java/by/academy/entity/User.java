package by.academy.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import static by.academy.util.Constants.BIRTH_DATE;
import static by.academy.util.Constants.USERS;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = USERS)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;

    @Column
    private String password;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column(name = BIRTH_DATE)
    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column
    private Role role;
}
