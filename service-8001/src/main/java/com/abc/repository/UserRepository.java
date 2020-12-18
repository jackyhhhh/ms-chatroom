package com.abc.repository;

import com.abc.bean.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findByStatus(int status);

    User findByUsername(String name);

}
