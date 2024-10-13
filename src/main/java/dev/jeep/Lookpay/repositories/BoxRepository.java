package dev.jeep.Lookpay.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface BoxRepository extends JpaRepository<BoxModel, Long> {
    @Query(value = "SELECT MAX(key) AS last_id FROM boxes WHERE branch_id = :branch_id", nativeQuery = true)
    public String getLastKey(@Param("branch_id") Long branchId);

    @Query(value = "SELECT * FROM boxes WHERE branch_id = :branch_id", nativeQuery = true)
    public List<BoxModel> getAllByBranchId(@Param("branch_id") Long branchId);
}
