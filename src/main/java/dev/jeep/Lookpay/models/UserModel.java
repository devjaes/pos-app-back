package dev.jeep.Lookpay.models;

import dev.jeep.Lookpay.models.bases.PersonBaseModel;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserModel extends PersonBaseModel {

    @Column(name = "password", nullable = false, length = 255)
    private String password;

    @ManyToOne
    @JoinColumn(name = "rol_id", nullable = false)
    private RolModel rol;

}
