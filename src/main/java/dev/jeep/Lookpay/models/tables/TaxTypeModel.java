package dev.jeep.Lookpay.models.tables;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tax_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TaxTypeModel {
    @Id
    @Column(name = "code_sri", nullable = false, length = 3)
    private String codeSri;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "taxType")
    private List<IvaTypeModel> ivaTypes;

    @OneToMany(mappedBy = "taxType")
    private List<IceTypeModel> iceTypes;

    @OneToMany(mappedBy = "taxType")
    private List<IrbpTypeModel> irbpnrTypes;
}
