package com.prasad.Login.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.prasad.Login.Entity.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer>{

	Optional<Login> findByEmail(String email);
}
