package dev.jeep.Lookpay.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "branches")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BranchModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "key", nullable = false, length = 3)
    private String key;

    @Column(name = "name", nullable = false, length = 300)
    private String name;

    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @OneToMany(mappedBy = "branch", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BoxModel> boxes;

}
