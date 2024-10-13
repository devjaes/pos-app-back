package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface InvoiceRepository extends JpaRepository<InvoiceModel, Long> {

}
