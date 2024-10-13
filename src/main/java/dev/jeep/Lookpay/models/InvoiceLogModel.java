package dev.jeep.Lookpay.models;

import java.sql.Date;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "invoices_log")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceLogModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "environment_type", nullable = false, length = 100)
    private String environmentType;

    @Column(name = "emission_type", nullable = false, length = 100)
    private String emissionType;

    @Column(name = "access_key", nullable = false, length = 49)
    private String accessKey;

    @Column(name = "receipt_type", nullable = false, length = 100)
    private String receiptType;

    @Column(name = "customer_identification", nullable = false, length = 20)
    private String customerIdentification;

    @Column(name = "emission_date", nullable = false)
    private Date emissionDate;

    @Column(name = "remission_guide", nullable = false, length = 15)
    private String remissionGuide;

    @Column(name = "total_without_tax", nullable = true)
    private Double totalWithoutTax;

    @Column(name = "total_discount", nullable = true)
    private Double totalDiscount;

    @Column(name = "tip", nullable = true)
    private Double tip;

    @Column(name = "payment_type", nullable = false, length = 100)
    private String paymentType;

    @Column(name = "total", nullable = true)
    private Double total;

    @Column(name = "currency", nullable = false, length = 20)
    private String currency;

    @Column(name = "box_key", nullable = false, length = 3)
    private String boxKey;

    @Column(name = "branch_name", nullable = false, length = 300)
    private String branchName;
}
