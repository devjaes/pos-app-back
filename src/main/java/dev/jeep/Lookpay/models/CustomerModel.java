package dev.jeep.Lookpay.models;

import java.util.List;

import dev.jeep.Lookpay.models.bases.PersonBaseModel;
import dev.jeep.Lookpay.models.tables.IdentificationTypeModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerModel extends PersonBaseModel {
    @Column(name = "business_name", nullable = false, length = 300)
    private String businessName;

    @Column(name = "identification", nullable = false, length = 20, unique = true)
    private String identification;

    @Column(name = "address", nullable = false, length = 255)
    private String address;

    @ManyToOne
    @JoinColumn(name = "identification_type_code", nullable = false)
    private IdentificationTypeModel identificationType;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InvoiceModel> invoices;

}
