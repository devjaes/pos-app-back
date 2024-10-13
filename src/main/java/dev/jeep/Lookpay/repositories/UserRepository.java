package dev.jeep.Lookpay.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import dev.jeep.Lookpay.models.*;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {
    @Query(value = "SELECT * FROM users WHERE email = :email", nativeQuery = true)
    public UserModel getUserByEmail(@Param("email") String email);
}
