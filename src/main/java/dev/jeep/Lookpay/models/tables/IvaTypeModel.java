package dev.jeep.Lookpay.models.tables;

import java.util.List;

import dev.jeep.Lookpay.models.ProductModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "iva_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class IvaTypeModel {
    @Id
    @Column(name = "code_sri", nullable = false, length = 3)
    private String codeSri;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "tax", nullable = false)
    private Double tax;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "tax_type_code", nullable = false)
    private TaxTypeModel taxType;

    @OneToMany(mappedBy = "ivaType")
    private List<ProductModel> products;
}
