package dev.jeep.Lookpay.models.tables;

import java.util.List;
import dev.jeep.Lookpay.models.InvoiceModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "receipt_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReceiptTypeModel {
    @Id
    @Column(name = "code_sri", nullable = false, length = 3)
    private String codeSri;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @OneToMany(mappedBy = "receiptType")
    private List<InvoiceModel> invoices;
}
