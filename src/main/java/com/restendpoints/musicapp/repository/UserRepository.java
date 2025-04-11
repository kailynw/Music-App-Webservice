package com.restendpoints.musicapp.repository;

import com.restendpoints.musicapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAuth0UserId(String auth0UserId);
}
