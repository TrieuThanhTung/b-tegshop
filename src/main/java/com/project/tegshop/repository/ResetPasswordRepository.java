package com.project.tegshop.repository;

import com.project.tegshop.model.ResetPasswordToken;
import com.project.tegshop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResetPasswordRepository extends JpaRepository<ResetPasswordToken, Integer> {
    @Query("select r from ResetPasswordToken r where r.user.userId = :id")
    Optional<ResetPasswordToken> findByUserId(@Param("id") Integer id);

    Optional<ResetPasswordToken> findByUser(UserEntity user);
}
