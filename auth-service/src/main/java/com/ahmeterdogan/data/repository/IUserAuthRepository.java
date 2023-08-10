package com.ahmeterdogan.data.repository;

import com.ahmeterdogan.data.entity.AuthTable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IUserAuthRepository extends JpaRepository<AuthTable, Long> {
    AuthTable findByUsernameAndPassword(String username, String password);
}
