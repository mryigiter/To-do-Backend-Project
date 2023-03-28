package com.yigiter.repository;


import com.yigiter.domain.User;
import org.springframework.data.jpa.repository.EntityGraph;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Boolean existsByEmail(String email);
    @EntityGraph(attributePaths = "roles")
    Optional<User>findByEmail(String email);

    @Modifying
    @Query("UPDATE User u SET u.firstName=:firstName, u.lastName=:lastName,u.phoneNumber=:phoneNumber,u.email=:email WHERE u.id=:id")
    void update(@Param("id") Long id,
                @Param("firstName") String firstName,
                @Param("lastName") String lastName,
                @Param("phoneNumber") String phoneNumber,
                @Param("email") String email);
}