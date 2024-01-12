package com.project.tegshop.repository;

import com.project.tegshop.model.RegisterToken;
import com.project.tegshop.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RegisterTokenRepository extends JpaRepository<RegisterToken, Integer> {

    @Query("select t from RegisterToken t where t.token = :token")
    Optional<RegisterToken> findByToken(@Param("token") String token);

    @Query("select t from RegisterToken t where t.user.userId = :id")
    Optional<RegisterToken> findByUserId(@Param("id") Integer id);
}
