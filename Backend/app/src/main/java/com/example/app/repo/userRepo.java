package com.example.app.repo;

import com.example.app.model.user;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * User repository interface that allows the API to communicate with the user table in the DB
 */

@Repository
public interface userRepo extends JpaRepository<user, Long> {

    //Find a user by userName
    @Query("select c from user c where c.userName = :userName")
    public user findByUserName(@Param("userName") String username);

    //Find a user by email
    @Query("select b from user b where b.email = :email")
    public user findByEmail(@Param("email") String email);

    //Find a user by id
    @Query("select d from user d where d.id = :id")
    public user findByID(@Param("id") Long userId);
}
