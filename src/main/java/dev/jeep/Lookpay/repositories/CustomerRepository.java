package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerModel, Long> {
    @Query(value = "SELECT * FROM customers WHERE identification = :identification", nativeQuery = true)
    public CustomerModel getCustomerByIdentification(@Param("identification") String identification);

    @Query(value = "SELECT * FROM customers WHERE email = :email", nativeQuery = true)
    public CustomerModel getCustomerByEmail(@Param("email") String email);

}
