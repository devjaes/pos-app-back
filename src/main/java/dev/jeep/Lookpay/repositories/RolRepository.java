package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface RolRepository extends JpaRepository<RolModel, Long> {
    @Query(value = "SELECT * FROM roles WHERE type = :type", nativeQuery = true)
    public RolModel getRolByType(@Param("type") String type);
}
