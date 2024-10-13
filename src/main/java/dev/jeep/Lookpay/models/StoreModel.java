package dev.jeep.Lookpay.models;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "stores")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StoreModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "store_name", nullable = false, length = 300)
    private String storeName;

    @Column(name = "trade_name", nullable = false, length = 300)
    private String tradeName;

    @Column(name = "ruc", nullable = false, length = 130)
    private String ruc;

    @Column(name = "address", nullable = false, length = 300)
    private String address;

    @Column(name = "especial_tax_payer", nullable = true, length = 13)
    private String especialTaxPayer;

    @Column(name = "forced_accounting", nullable = false, length = 300)
    private Boolean forcedAccounting;

    @Column(name = "electronic_signature_key", nullable = true)
    private String electronicSignatureKey;
}
