package com.stockproject.repository;

import com.stockproject.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories()
public interface UserRepository extends JpaRepository<User,Integer> {
    @Query("select u from User u where u.username = :username")
    public User findByUsername(@Param("username") String username);

}
