package dev.jeep.Lookpay.models;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "boxes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BoxModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "key", nullable = false, length = 3)
    private String key;

    @Column(name = "sequential", nullable = false, length = 9)
    private String sequential;

    @ManyToOne
    @JoinColumn(name = "branch_id", nullable = false)
    private BranchModel branch;

    @OneToMany(mappedBy = "box", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceModel> invoices;
}
