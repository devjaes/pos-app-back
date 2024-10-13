package dev.jeep.Lookpay.models.tables;

import java.util.List;
import dev.jeep.Lookpay.models.InvoiceModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "payment_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTypeModel {
    @Id
    @Column(name = "code_sri", nullable = false, length = 3)
    private String codeSri;

    @Column(name = "type", nullable = false, length = 100)
    private String type;

    @Column(name = "status", nullable = false)
    private Boolean status;

    @Column(name = "start_date", nullable = false, length = 20)
    private String startDate;

    @Column(name = "ending_date", nullable = true, length = 20)
    private String endingDate;

    @OneToMany(mappedBy = "paymentType")
    private List<InvoiceModel> invoices;
}
