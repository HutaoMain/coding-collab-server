package com.codecat.codecat.repository;

import com.codecat.codecat.model.Classes;
import com.codecat.codecat.model.User;
import com.codecat.codecat.model.UserClasses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserClassesRepository extends JpaRepository<UserClasses, Long> {

    List<UserClasses> findByUser(User user);

    List<UserClasses> findByClasses(Classes classes);
}
