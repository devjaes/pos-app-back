package dev.jeep.Lookpay.repositories.tables;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.tables.*;

@Repository
public interface EnvironmentTypeRepository extends JpaRepository<EnvironmentTypeModel, String> {
    @Query(value = "SELECT * FROM environment_types WHERE type = :type", nativeQuery = true)
    public EnvironmentTypeModel getEnvironmentByType(@Param("type") String type);
}
